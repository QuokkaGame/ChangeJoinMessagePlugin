# 🎉 Change Join Message Plugin

[![Java](https://img.shields.io/badge/Java-21-red)]() [![Paper](https://img.shields.io/badge/Paper-1.21+-blue)](https://papermc.io/downloads/paper) [![License: MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE) [![LuckPerms](https://img.shields.io/badge/LuckPerms-Supported-brightgreen)](https://luckperms.net/download)

## 🚀 Overview
**ChangeJoinMessagePlugin** allows you to customize the join and leave messages in Minecraft!  
Control exactly how players' join/leave actions are displayed, with support for prefixes, suffixes, and display names.

⚠️ **Note:** This plugin **requires LuckPerms** for full functionality.

## ✨ Features
✅ **Customizable join and leave messages**: Modify the default join/leave notifications  
✅ **LuckPerms Integration**: Supports **prefixes & suffixes** in messages  
✅ **Configurable toggle options**: Easily enable/disable messages with `config.yml`  
✅ **Simple reload command**: Apply config changes instantly

## 📥 Installation
1. **[Download the latest version](https://github.com/QuokkaGame/ChangeJoinMessagePlugin/releases)**
2. Place the `.jar` file into your `plugins/` folder
3. Restart your Minecraft server

## ⚙️ Configuration (`config.yml`)
```yaml
# Toggle options for join and leave messages
join-message-toggle: true
leave-message-toggle: true

# Customize the format of join and leave messages
join-message: "§e{prefix}{name}{suffix} joined the game"
leave-message: "§e{prefix}{name}{suffix} left the game"
```
## 📋 Placeholders:
- `{prefix}`: Player's LuckPerms prefix
- `{name}`: Player's in-game name
- `{displayname}`: Player's display name
- `{suffix}`: Player's LuckPerms suffix

## 🛠️ Commands
```bash
/changejoinmessage reload
```
Reloads the plugin configuration.

## 🛑 Compatibility Warning
This plugin **requires LuckPerms** for prefix and suffix support.  
It is **not compatible with other join message plugins**.

## 📜 License
This project is licensed under the MIT License.  
See [`LICENSE`](LICENSE) for more details.
