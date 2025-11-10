# <img src="../docs/images/calcifer.jpg" width="40" height="40"/> TestApp - Android TCP Messaging Client

## Overview
**TestApp** is a simple Android application that allows users to send text messages over TCP to a specified server IP address.  
The app is designed to demonstrate basic network communication and asynchronous message sending using modern Android practices.

---

## Features
- Send a custom text message to any server over TCP (port 3333 by default)
- Specify the server IP address via a simple input field
- Asynchronous network communication using `ExecutorService` to avoid blocking the UI thread
- User feedback with Toast notifications for success and error messages
- Edge-to-edge layout support for modern Android UI

---

## Screenshots
<p align="center">
  <img src="../docs/images/app_screen.jpg" width="300" alt="App screenshot"/>
</p>
*Screenshot of the app interface*

---

## Installation & Setup

### Requirements
- Android Studio 
- Android SDK 34+ 
- Minimum Android version: 21 
- A server listening on TCP port 3333 (same network as the device)
