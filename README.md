# Strobo

Strobo is a lighting control app designed for use in house parties.
Currently, Strobo offers basic lighting control features, with a roadmap to introduce scriptable shows, parametric
effects, music synchronization and Spotify integration.

> [!NOTE]  
> This project is still in development.

## Features

- [x] **Basic Light Control**: Easily control your lighting setup.
- [ ] **Scriptable Shows**: In future updates, expect to create custom light shows using JavaScript.
- [ ] **Parametric Effects**: I'm working on adding parametric controls for designing complex lighting effects.
- [ ] **Music Synchronization**: Plans are in place to enable your lights to sync with music.
- [ ] **Spotify Integration**: The music synchronization feature will be integrated with Spotify.

## Projects

### Backend

The backend serves as the central command and control hub.
It's responsible for orchestrating the lighting system, managing the database, and providing a REST API for the
frontend.

![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Kotlin](https://img.shields.io/badge/-Kotlin-0095D5?style=flat-square&logo=kotlin&logoColor=white)
![MQTT](https://img.shields.io/badge/-MQTT-6B086B?style=flat-square&logo=mqtt&logoColor=white)

### Slave

Each slave is a microcontroller that controls a single light device.

![ESP32](https://img.shields.io/badge/-ESP32-00979D?style=flat-square&logo=espressif&logoColor=white)
![MQTT](https://img.shields.io/badge/-MQTT-6B086B?style=flat-square&logo=mqtt&logoColor=white)

### App

The frontend is a web app that provides a user interface for controlling the lighting system.

![Angular](https://img.shields.io/badge/-Angular-DD0031?style=flat-square&logo=angular&logoColor=white)
![Ionic](https://img.shields.io/badge/-Ionic-3880FF?style=flat-square&logo=ionic&logoColor=white)

## Installation

### Prerequisites

- Node.js
- Docker
- Java 17

### Step 1: Clone the repository

```shell
git clone https://github.com/danielkreitsch/strobo.git
```

### Step 2: Navigate to the repository directory

```shell
cd strobo
```

### Step 3: Install dependencies

```shell
npm install
```

## Running the Projects

### Backend

To serve the database via Docker, use the following command:

```shell
nx infra
```

To serve the backend, use the following command:

```shell
nx serve backend
```

### Slave

More information on how to flash the firmware onto the ESP32 or ESP8266 will be provided in the future.

### App

To serve the frontend app, use the following command:

```shell
nx serve app
```
