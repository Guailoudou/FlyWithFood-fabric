# FlyWithFood - Fabric Mod

一个基于饥饿值消耗的飞行模组，允许玩家在消耗饥饿值的情况下飞行。

## 功能特性

- **饥饿值飞行**：玩家可以启用飞行功能，飞行时会持续消耗饥饿值
- **自动关闭**：当饥饿值低于阈值时，自动关闭飞行能力
- **服务端模组**：仅需在服务端安装，客户端无需安装即可使用
- **可配置**：支持自定义饥饿消耗速率、最低饥饿阈值和提示消息
- **权限支持**：支持 LuckPerms 权限管理，无 LuckPerms 时使用 OP 权限
- **可配置的饥饿消耗间隔**：可自定义饥饿值消耗的时间间隔

## 命令

| 命令 | 描述 | 权限节点 |
|------|------|----------|
| `/fly` | 切换飞行状态（开/关） | `flywithfood.use` |
| `/fly on` | 启用飞行 | `flywithfood.use` |
| `/fly off` | 禁用飞行 | `flywithfood.use` |
| `/fly reload` | 重载配置文件 | `flywithfood.reload` |

## 权限节点

| 权限节点 | 描述 | 默认 |
|----------|------|------|
| `flywithfood.use` | 允许使用飞行命令 | 所有人 |
| `flywithfood.reload` | 允许重载配置文件 | 管理员 (OP) |

### LuckPerms 配置示例

如果安装了 LuckPerms，可以使用以下命令配置权限：

```bash
# 给玩家使用飞行权限
/lp user <玩家名> permission set flywithfood.use true

# 给玩家重载配置权限
/lp user <玩家名> permission set flywithfood.reload true

# 给权限组使用飞行权限
/lp group <权限组> permission set flywithfood.use true
```

> **注意**：如果没有安装 LuckPerms，模组会自动使用 OP 权限系统。`flywithfood.use` 默认所有人可用，`flywithfood.reload` 需要 OP 权限。

## 配置文件

配置文件位于：`config/flywithfood.json`

```json
{
  "hungerDrainPerSecond": 1.0,
  "minHungerToFly": 6,
  "hungerDrainIntervalTicks": 20,
  "messageFlyEnabled": "§a[FlyWithFood] 飞行已启用！飞行时会消耗饥饿值。",
  "messageFlyDisabled": "§c[FlyWithFood] 飞行已禁用！",
  "messageHungerLow": "§c[FlyWithFood] 饥饿值不足，飞行已自动关闭！",
  "messageConfigReloaded": "§a[FlyWithFood] 配置文件已重新加载！"
}
```

### 配置项说明

| 配置项 | 类型 | 默认值 | 描述 |
|--------|------|--------|------|
| `hungerDrainPerSecond` | double | 1.0 | 每次消耗的饥饿值 |
| `minHungerToFly` | int | 6 | 允许飞行的最低饥饿值（饥饿值低于此值时自动关闭飞行） |
| `hungerDrainIntervalTicks` | int | 20 | 饥饿值消耗的间隔（单位：刻，20刻=1秒） |
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
- LuckPerms（可选，用于权限管理）

## 安装方法

1. 确保已安装 Fabric Loader 和 Fabric API
2. 将模组 JAR 文件放入服务器的 `mods` 文件夹
3. （可选）安装 LuckPerms 以使用权限管理功能
4. 启动服务器，模组会自动生成配置文件
5. 根据需要修改配置文件

## License

MIT License
