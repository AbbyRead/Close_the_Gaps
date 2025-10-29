# Close the Gaps

A small graphical fix addon for the Better Than Wolves CE 3.0.0 Minecraft mod.

## Overview

Close the Gaps eliminates visible space between the edges of sprite-based item and block models by removing unnecessary texture UV padding that was originally added to prevent atlas bleeding artifacts.

## The Problem

When textures are packed into an atlas, neighboring textures can "bleed" into each other at the edges—a problem called **atlas bleeding**. To prevent this, Minecraft applies inward padding to UV coordinates, effectively shrinking each texture slightly within its allocated atlas space.

However, this transparent padding causes visible gaps to appear in models, albeit slight.  It's most noticeable along the edges of sprite-based objects rendered three-dimensionally.  It makes things that should look solid appear fractured and hollow.

## The Solution

This addon removes the UV padding entirely, allowing item textures to use their full allocated atlas space. This eliminates the gaps while maintaining texture quality.

The fix is implemented as a single Mixin that modifies `TextureAtlasSprite.initSprite()` to skip the padding calculation that was causing the issue.

## Screenshots
It's a bit hard to tell on my not-very-extreme-resolution monitor setup, but...
<p align="center"><img  width="49%" alt="Before" src="https://github.com/user-attachments/assets/3b2cc76d-35b0-4cb5-982c-6196481a6b10" /> <img width="49%" alt="After" src="https://github.com/user-attachments/assets/0c7eaae3-b3fa-46d4-be32-f0897e164e1b" /></p>

## Compatibility Note

This approach works well on most modern systems. However, if your GPU or texture pack configuration experiences atlas bleeding issues (textures bleeding into neighboring textures), you may see that problem reappear. If this occurs, you can safely disable this addon without giving it a second thought.  Just throw it in the trash!

## Inspiration

This fix was inspired by the [Model Gap FIX](https://www.curseforge.com/minecraft/mc-mods/model-gap-fix) mod for newer Minecraft versions, which uses the same approach to solve an identical problem in modern versions of vanilla Minecraft.

---

## Installation

1. Install Better Than Worlds: Community Edition 3.0.0 + Legacy Fabric by following the instructions on the [wiki](https://wiki.btwce.com/view/3.0.0_Beta).
2. Download this addon's JAR file from the Releases page.
3. Place the addon JAR file in your `.minecraft/mods` folder.
4. Launch Minecraft. Item models will now render without visible gaps.

---

## Compatibility

* **Required**: Better Than Worlds CE 3.0.0
* **Mod Loader**: Fabric/Mixin based (Packaged with the BTW Instance)
* Works with all item types and texture packs.
* No known conflicts at this time.

---

## Technical Details

This addon consists of a single mixin that patches `TextureAtlasSprite.initSprite()`. Instead of applying inward UV padding to prevent atlas bleeding, it uses the full texture coordinates for each sprite. This is a minimal, non-intrusive change that affects only UV coordinate calculation—no rendering logic is modified.

---

## License

This project is released under the [BSD Zero-Clause License](LICENSE).
You're free to use, modify, and share it however you see fit.

---

## Credits

* **Addon author**: Abigail Read
* **Better Than Worlds**: Created by *FlowerChild*, continued by the BTW Community
* **Inspiration**: [Model Gap FIX](https://www.curseforge.com/minecraft/mc-mods/model-gap-fix) mod for modern Minecraft
* Thanks to the **Legacy Fabric team** for keeping classic modding alive.

---

*"Problems start when you sell the intent and not the reality."* &ensp;&ndash; Doug Wagner
</br><small>[The Gap Between Intention and Reality](https://www.sunwaptasolutions.com/2011/04/the-gap-between-intention-and-reality)</small>
