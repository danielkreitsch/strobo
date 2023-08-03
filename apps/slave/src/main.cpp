#define deviceType 31

#define WS2812 0
#define SK6812 1

#if deviceType == 1
#define ledDeviceId "kuechentheke"
#define ledDeviceType SK6812
#define totalPixelCount 118
#endif
#if deviceType == 2
#define ledDeviceId "alkohololregal-oben"
#define ledDeviceType WS2812
#define totalPixelCount 55
#endif
#if deviceType == 3
#define ledDeviceId "alkoholregal-unten"
#define ledDeviceType WS2812
#define totalPixelCount 55
#endif
#if deviceType == 4
#define ledDeviceId "flurtisch"
#define ledDeviceType WS2812
#define totalPixelCount 48
#endif
#if deviceType == 5
#define ledDeviceId "saufschild"
#define ledDeviceType SK6812
#define totalPixelCount 102
#endif
#if deviceType == 6
#define ledDeviceId "miniregal"
#define ledDeviceType WS2812
#define totalPixelCount 8
#endif
#if deviceType == 7
#define ledDeviceId "teststreifen"
#define ledDeviceType WS2812
#define totalPixelCount 10
#endif

#if deviceType == 21
#define ledDeviceId "dorfladen-s-oben"
#define ledDeviceType WS2812
#define totalPixelCount 60
#endif
#if deviceType == 22
#define ledDeviceId "dorfladen-s-unten"
#define ledDeviceType WS2812
#define totalPixelCount 60
#endif
#if deviceType == 23
#define ledDeviceId "dorfladen-g-oben"
#define ledDeviceType WS2812
#define totalPixelCount 60
#endif
#if deviceType == 24
#define ledDeviceId "dorfladen-g-unten"
#define ledDeviceType WS2812
#define totalPixelCount 60
#endif

#if deviceType == 31
#define ledDeviceId "adventskalender"
#define ledDeviceType WS2812
#define totalPixelCount 99
#endif

#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <PubSubClient.h> // by Nick
#include <ArduinoQueue.h> // by Einar
#include <NeoPixelBus.h>  // by Makuna

typedef struct
{
  byte r;
  byte g;
  byte b;
  byte w;
} Color;

typedef struct
{
  unsigned long t;
  Color colorArray[totalPixelCount];
} Keyframe;

// WiFi configuration
const char *ssid = "Nuss";
const char *password = "NattoSukiDesuKa?";

// MQTT configuration
const char *mqttHost = "server.glowdragon.de";
const int mqttPort = 1883;
const char *mqttUsername = "strobo:strobo";
const char *mqttPassword = "1234Code";
const char *newDeviceQueue = "device-connecting";
const char *pingQueue = "device-health";
const char *debugQueue = "device-log";

// Communication
WiFiClient wifiClient;
PubSubClient client(wifiClient);
unsigned long nextPingTime;
bool connectedOnce;

// Time
unsigned long currentTime = 0;
unsigned long connectedTime = 0;
unsigned long timeSinceReset = 0;

// Queue
ArduinoQueue<Keyframe> queue(20000);
Keyframe nextKeyframe;
unsigned long nextKeyframeTime = 0;
bool consumeNextKeyframe = true;

// Debugging
unsigned long nextDebugMessageTime;

// LEDs
#if ledDeviceType == SK6812
NeoPixelBus<NeoGrbwFeature, NeoEsp8266Dma800KbpsMethod> strip(totalPixelCount);
#endif
#if ledDeviceType == WS2812
NeoPixelBus<NeoGrbFeature, NeoEsp8266Dma800KbpsMethod> strip = NeoPixelBus<NeoGrbFeature, NeoEsp8266Dma800KbpsMethod>(totalPixelCount);
#endif
unsigned long lastStripUpdateTime;

// Temp variables (for efficiency)
Color temp_color;

// Function prototypes
void setupWifi();
void handleCommunication();
void handleQueue();
void handleDebugging();
void onMessageReceive(char *topic, byte *message, unsigned int length);
void connectMessageBroker();
void connectToMaster();
void pingMaster();
void printFreeHeap();
void sendDebugMessage(String message, bool online);
void resetQueue();
void setAllLEDs(int r, int g, int b);
void setStatusLEDs(int r, int g, int b);

void setup()
{
  Serial.begin(115200);

  strip.Begin();
  setAllLEDs(0, 0, 0);
  setStatusLEDs(255, 255, 255);

  setupWifi();

  client.setServer(mqttHost, mqttPort);
  client.setCallback(onMessageReceive);
  client.setBufferSize(128 + totalPixelCount * 4);
}

void loop()
{
  currentTime = millis();
  timeSinceReset = currentTime - connectedTime;

  handleCommunication();
  handleQueue();
  // handleDebugging();
}

void handleCommunication()
{
  if (currentTime >= nextPingTime)
  {
    pingMaster();
    nextPingTime = currentTime + 1000;
  }

  if (!client.connected())
  {
    connectMessageBroker();
  }
  client.loop();
}

void handleQueue()
{
  if (consumeNextKeyframe)
  {
    if (queue.itemCount() == 0)
    {
      return;
    }
    nextKeyframe = queue.dequeue();
    nextKeyframeTime = nextKeyframe.t;
    consumeNextKeyframe = false;
  }

  if (timeSinceReset >= nextKeyframeTime && lastStripUpdateTime < currentTime - 33)
  {
    for (int pixel = 0; pixel < totalPixelCount; pixel++)
    {
      temp_color = nextKeyframe.colorArray[pixel];
#if ledDeviceType == SK6812
      strip.SetPixelColor(pixel, RgbwColor(temp_color.r, temp_color.g, temp_color.b, temp_color.w));
#endif
#if ledDeviceType == WS2812
      strip.SetPixelColor(pixel, RgbColor(temp_color.r, temp_color.g, temp_color.b));
#endif
    }
    strip.Show();
    lastStripUpdateTime = currentTime;

    consumeNextKeyframe = true;
  }
}

void handleDebugging()
{
  if (currentTime >= nextDebugMessageTime)
  {
    nextDebugMessageTime = currentTime + 1000;

    Serial.print("[Info] Time: ");
    Serial.print(currentTime);
    Serial.print(", Time since reset: ");
    Serial.print(timeSinceReset);
    Serial.print(", Next frame: ");
    Serial.print(nextKeyframeTime);
    Serial.print(", Queue: ");
    Serial.print(queue.itemCount());
    Serial.print(", ");
    printFreeHeap();
    Serial.println("");
  }
}

void onMessageReceive(char *topic, byte *message, unsigned int length)
{
  if (length == 1)
  {
    connectToMaster();
    return;
  }

  Keyframe keyframe;

  keyframe.t = 0;
  keyframe.t += message[0] << 24;
  keyframe.t += message[1] << 16;
  keyframe.t += message[2] << 8;
  keyframe.t += message[3];

  if (keyframe.t < timeSinceReset - 1000)
  {
    sendDebugMessage("Rejected keyframe because it's too old", false);
    return;
  }

  if (keyframe.t > timeSinceReset + 2000)
  {
    sendDebugMessage("Rejected keyframe because it's scheduled to far in the future", false);
    return;
  }

  for (int pixel = 0; pixel < totalPixelCount; pixel++)
  {
    keyframe.colorArray[pixel] = {
        message[4 + 4 * pixel],
        message[4 + 4 * pixel + 1],
        message[4 + 4 * pixel + 2],
        message[4 + 4 * pixel + 3]};
  }

  queue.enqueue(keyframe);
}

void connectMessageBroker()
{
  while (!client.connected())
  {
    Serial.println("[MQTT] Connecting to message broker...");
    // Attempt to connect
    if (client.connect(ledDeviceId, mqttUsername, mqttPassword))
    {
      Serial.println("[MQTT] Successfully connected.");
      setStatusLEDs(0, 255, 0);
      client.subscribe(ledDeviceId);
      resetQueue();
      connectToMaster();
    }
    else
    {
      Serial.print("[MQTT] Failed connecting (rc=");
      Serial.print(client.state());
      Serial.println("), trying again in 3 seconds.");
      setStatusLEDs(255, 0, 0);
      delay(3000);
    }
  }

  if (!connectedOnce)
  {
    connectedOnce = true;
  }
  else
  {
    sendDebugMessage("Had to reconnect to AMQP", true);
  }
}

void connectToMaster()
{
  String deviceInfoJson;
  deviceInfoJson += "{\"id\": \"";
  deviceInfoJson += ledDeviceId;
  deviceInfoJson += "\", \"type\": ";
  deviceInfoJson += ledDeviceType;
  deviceInfoJson += ", \"ledCount\": ";
  deviceInfoJson += totalPixelCount;
  deviceInfoJson += "}";
  client.publish(newDeviceQueue, deviceInfoJson.c_str());

  connectedTime = millis();
}

void pingMaster()
{
  String json;
  json += "{\"ledDeviceId\": \"";
  json += ledDeviceId;
  json += "\"}";
  client.publish(pingQueue, json.c_str());
}

void setupWifi()
{
  delay(10);

  Serial.println();
  Serial.print("[WiFi] Connecting to ");
  Serial.println(ssid);
  Serial.print("[WiFi] ");

  setStatusLEDs(255, 165, 0);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.print("[WiFi] WiFi connected (IP: ");
  Serial.print(WiFi.localIP());
  Serial.println(")");
  setStatusLEDs(255, 255, 0);
}

void printFreeHeap()
{
  long fh = ESP.getFreeHeap();
  char fhc[20];
  ltoa(fh, fhc, 10);
  String freeHeap = String(fhc);
  Serial.print("Free RAM: ");
  Serial.print(freeHeap);
  Serial.print(" B");
}

void sendDebugMessage(String message, bool online)
{
  Serial.println(message);
  if (online)
  {
    client.publish(debugQueue, message.c_str());
  }
}

void resetQueue()
{
  while (!queue.isEmpty())
  {
    queue.dequeue();
  }
  consumeNextKeyframe = true;
}

void setAllLEDs(int r, int g, int b)
{
  for (int pixel = 0; pixel < totalPixelCount; pixel++)
  {
#if ledDeviceType == SK6812
    strip.SetPixelColor(pixel, RgbwColor(r, g, b, 0));
#endif
#if ledDeviceType == WS2812
    strip.SetPixelColor(pixel, RgbColor(r, g, b));
#endif
  }
  strip.Show();
}

void setStatusLEDs(int r, int g, int b)
{
  /*#if ledDeviceType == SK6812
      strip.SetPixelColor(0, RgbwColor(r, g, b, 0));
      strip.SetPixelColor(totalPixelCount - 1, RgbwColor(r, g, b, 0));
  #endif
  #if ledDeviceType == WS2812
      strip.SetPixelColor(0, RgbColor(r, g, b));
      strip.SetPixelColor(totalPixelCount - 1, RgbColor(r, g, b));
  #endif
    strip.Show();*/
  for (int pixel = 0; pixel < totalPixelCount; pixel++)
  {
#if ledDeviceType == SK6812
    strip.SetPixelColor(pixel, RgbwColor(r, g, b, 0));
#endif
#if ledDeviceType == WS2812
    strip.SetPixelColor(pixel, RgbColor(r, g, b));
#endif
  }
  strip.Show();
}
