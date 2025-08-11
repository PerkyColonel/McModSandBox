# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft Forge mod project using the official Minecraft Development Kit (MDK) template. The mod is currently configured as "TestMod" (ID: `examplemod`) and targets Minecraft 1.21.1 with Forge 52.1.0.

## Build System and Commands

This project uses Gradle with ForgeGradle plugin. Key commands:

### Development Setup
- `./gradlew genEclipseRuns` - Generate Eclipse run configurations
- `./gradlew genIntellijRuns` - Generate IntelliJ IDEA run configurations
- `./gradlew --refresh-dependencies` - Refresh dependency cache
- `./gradlew clean` - Clean build artifacts

### Build and Run
- `./gradlew build` - Build the mod JAR
- `./gradlew runClient` - Launch Minecraft client with mod loaded
- `./gradlew runServer` - Launch dedicated server with mod loaded
- `./gradlew runGameTestServer` - Run automated game tests
- `./gradlew runData` - Generate mod data (recipes, loot tables, etc.)

### Configuration
- Java 21 is required (as specified in build.gradle)
- Memory: Gradle is configured with 3GB heap (`org.gradle.jvmargs=-Xmx3G`)
- The mod uses official Mojang mappings by default

## Code Architecture

### Package Structure
- Base package: `com.example.examplemod`
- Main mod class: `ExampleMod.java` - Entry point with `@Mod` annotation
- Configuration: `Config.java` - Forge config system with validation

### Minecraft Forge Patterns
- **DeferredRegister**: Used for registering blocks, items, and creative tabs
- **RegistryObject**: Type-safe references to registered objects
- **Event Bus**: Two buses - MOD bus for mod lifecycle, FORGE bus for game events
- **Config System**: ForgeConfigSpec with automatic validation and reloading

### Key Components
- **Registry Management**: All registrations done through DeferredRegister in main mod class
- **Creative Tabs**: Custom creative tab system with proper icon and item management  
- **Client/Server Split**: `@Mod.EventBusSubscriber` with `Dist.CLIENT` for client-only code
- **Config Integration**: Automatic config loading with validation and type conversion

### Resource Structure
- `src/main/resources/META-INF/mods.toml` - Mod metadata with property substitution
- `src/main/resources/pack.mcmeta` - Resource pack metadata
- `src/generated/resources/` - Generated data from data generators

## Development Notes

- Mod properties are defined in `gradle.properties` and substituted into resources
- The mod ID must match between `@Mod` annotation, `gradle.properties`, and `mods.toml`
- Use SLF4J logger (`LogUtils.getLogger()`) for logging, not System.out
- Official mappings require awareness of Mojang license terms
- Resources and classes are merged into single directory for Java module compatibility