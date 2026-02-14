# FlyWithFood - Fabric Mod

一个基于饥饿值消耗的飞行模组，允许玩家在消耗饥饿值的情况下飞行。

## 功能特性

- **饥饿值飞行**：玩家可以启用飞行功能，飞行时会持续消耗饥饿值
- **自动关闭**：当饥饿值低于阈值时，自动关闭飞行能力
- **服务端模组**：仅需在服务端安装，客户端无需安装即可使用
- **可配置**：支持自定义饥饿消耗速率、最低饥饿阈值和提示消息

## 命令

| 命令 | 描述 |
|------|------|
| `/fly` | 切换飞行状态（开/关） |
| `/fly on` | 启用飞行 |
| `/fly off` | 禁用飞行 |
| `/fly reload` | 重载配置文件 |

## 配置文件

配置文件位于：`config/flywithfood.json`

```json
{
  "hungerDrainPerSecond": 1.0,
  "minHungerToFly": 6,
  "messageFlyEnabled": "§a[FlyWithFood] 飞行已启用！飞行时会消耗饥饿值。",
  "messageFlyDisabled": "§c[FlyWithFood] 飞行已禁用！",
  "messageHungerLow": "§c[FlyWithFood] 饥饿值不足，飞行已自动关闭！",
  "messageConfigReloaded": "§a[FlyWithFood] 配置文件已重新加载！"
}
```

### 配置项说明

| 配置项 | 类型 | 默认值 | 描述 |
|--------|------|--------|------|
| `hungerDrainPerSecond` | double | 1.0 | 每秒消耗的饥饿值 |
| `minHungerToFly` | int | 6 | 允许飞行的最低饥饿值（饥饿值低于此值时自动关闭飞行） |
| `messageFlyEnabled` | String | §a[FlyWithFood] 飞行已启用！... | 启用飞行时显示的消息 |
| `messageFlyDisabled` | String | §c[FlyWithFood] 飞行已禁用！ | 禁用飞行时显示的消息 |
| `messageHungerLow` | String | §c[FlyWithFood] 饥饿值不足... | 饥饿值不足时显示的消息 |
| `messageConfigReloaded` | String | §a[FlyWithFood] 配置文件已重新加载！ | 重载配置时显示的消息 |

> **提示**：消息支持 Minecraft 颜色代码，如 `§a`（绿色）、`§c`（红色）等

## 安装要求

- Minecraft 1.21.11
- Fabric Loader 0.18.2+
- Fabric API
- Java 21+

## 安装方法

1. 确保已安装 Fabric Loader 和 Fabric API
2. 将模组 JAR 文件放入服务器的 `mods` 文件夹
3. 启动服务器，模组会自动生成配置文件
4. 根据需要修改配置文件

## License

MIT License
