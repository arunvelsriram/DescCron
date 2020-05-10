# DescCron

DescCron (Describe Cron) is a plugin for [JetBrains IDEs](https://www.jetbrains.com/products.html#type=ide), that provides a descriptive view of cron expressions for humans.

It's file format agnostic, as the cron expression to be described is a user selected text.

![Demo for DescCron](./DescCron.gif)

[Plugin Homepage](https://plugins.jetbrains.com/plugin/14159-desccron)

## How to use?

### Popup Menu

* Highlight the cron expression
* Use the options under DescCron group from the Popup Menu

### Keyboard Shortcut

* Highlight the cron expression
* Use the default keybindings:

#### Mac OS X

|Action   |Keybinding|
|:-------|:--------:|
|Describe |⌘ ⇧ D, C |
|Next Run |⌘ ⇧ D, N |
|Previous Run |⌘ ⇧ D, P |

#### Linux / Windows
|Action    |Keybinding  |
|:--------|:----------:|
|Describe  |⌃ ⇧ D, C    |
|Next Run  |⌃ ⇧ D, N    |
|Previous Run  |⌃ ⇧ D, N    |

## Development

```bash
./gradlew clean build
```
