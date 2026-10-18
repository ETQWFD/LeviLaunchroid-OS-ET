<div align="center">

# LeviLauncher OS ET

**一个轻量的 Minecraft 基岩版安卓启动器，支持 32 位 / 64 位，免谷歌三件套运行。**

![Android](https://img.shields.io/badge/Android-9.0%2B-green)
![Arch](https://img.shields.io/badge/ABI-arm64--v8a%20%7C%20armeabi--v7a-blue)
![License](https://img.shields.io/badge/License-Apache--2.0-orange)

</div>

## 这是什么

LeviLauncher OS ET 是在 LeviLaunchroid 基础上整理的版本。导入你自己拥有授权的 Minecraft 基岩版 APK，即可在不安装到系统的情况下运行，并支持外置原生模块、多版本隔离、资源包与世界管理。

相比原项目，本版做了三件事：

- **支持 32 位手机**：新增 `armeabi-v7a` 原生构建，旧的 32 位安卓机也能安装打开。
- **免谷歌三件套**：移除对 Google Play 服务、Play Games、Firebase（推送 / 崩溃上报）的运行依赖，不装谷歌服务也能用。
- **更新走本仓库**：应用内检查更新指向当前仓库的 Release。

> 仅 64 位内置的 `gxcore / maesdk / PlayFabMultiplayer` 在 32 位设备上会自动跳过，不影响启动器主体和大部分功能；64 位设备功能与原版一致。

## 下载

到 [Releases](https://github.com/ETQWFD/LeviLaunchroid-OS-ET/releases) 下载最新 APK，安装即可。需要 Android 9.0 及以上。

## 联系

- 作者邮箱：2416444244@qq.com
- QQ 群：[点击加入](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=UxrfqNnJERsNXBPZ3mjlQPCeGl3iti-d&authKey=u9Vs5w%2FC9tUFH4kpxuVx%2BatmXLeu8H05Ubjm%2BVABDH5z56dG%2Bj8hYa0ggctAwtcn&noverify=0&group_code=1104692081)（群号 1104692081）

## 自行编译

需要 JDK 21、Android SDK（Platform 36、Build-Tools 35.0.0）、NDK 28.2.13676358、CMake。

```bash
git clone --recursive https://github.com/ETQWFD/LeviLaunchroid-OS-ET.git
cd LeviLaunchroid-OS-ET
./gradlew :app:assembleRelease
```

产物在 `app/build/outputs/apk/release/`。默认使用调试签名；要发布可在仓库根目录放置 `keystore.properties`（参考应用构建脚本中的字段）。

## 许可证

本项目沿用原 LeviLaunchroid 的 **Apache License 2.0**，LICENSE 文件保持不变。Minecraft 是 Mojang / Microsoft 的商标，需要你自行拥有正版授权。
