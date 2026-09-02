#!/usr/bin/env python3
"""Rebuild the launcher icon from tools/icon/source.jpg.

The owner supplied the artwork as a square JPG with a white margin around a
rounded-square badge. Three things had to be decided, and they are decided here
rather than by hand so the whole set can be regenerated from one command:

  1. THE WHITE MARGIN IS CROPPED. It is part of the picture, not the icon; left
     in, every launcher would draw a white ring.

  2. THE BADGE IS INSET, NOT FULL BLEED. An adaptive icon is 108dp and a
     launcher only guarantees the centre 66dp circle; the smallest real mask
     shows 72dp. This artwork's side bubbles and their sound-wave arcs sit close
     to the badge edge, so at full bleed a circular mask would cut "Hello" and
     "नमस्ते" off entirely. The badge is therefore scaled to 68% of the canvas
     and centred, which keeps every element inside the visible area.

  3. THE BACKGROUND IS A GRADIENT SAMPLED FROM THE BADGE ITSELF, so the inset
     badge does not float on a flat block of a colour that is not in the art.
     Corners are read from inside the badge's rounded corners.

Legacy PNG mipmaps are written too: minSdk is 24, and API 24-25 cannot parse
<adaptive-icon>. Those densities are NOT masked by the launcher, so they get the
badge full bleed, which is how the artwork was drawn.

Run:  python3 tools/icon/make_icons.py
"""
import os
from PIL import Image, ImageDraw

HERE = os.path.dirname(os.path.abspath(__file__))
RES = os.path.join(HERE, "..", "..", "app", "src", "main", "res")

src = Image.open(os.path.join(HERE, "source.jpg")).convert("RGB")
W, H = src.size
px = src.load()

# --- 1. crop the white margin -------------------------------------------------
def nonwhite(p): return not (p[0] > 240 and p[1] > 240 and p[2] > 240)
xs = [x for x in range(W) if any(nonwhite(px[x, y]) for y in range(0, H, 4))]
ys = [y for y in range(H) if any(nonwhite(px[x, y]) for x in range(0, W, 4))]
badge = src.crop((xs[0], ys[0], xs[-1] + 1, ys[-1] + 1))
side = min(badge.size)
bw, bh = badge.size
badge = badge.crop(((bw - side) // 2, (bh - side) // 2,
                    (bw - side) // 2 + side, (bh - side) // 2 + side))
print("badge", badge.size)

# --- 2. background gradient, sampled from inside the badge's rounded corners ---
bp = badge.load()
i = int(side * 0.16)
tl, tr = bp[i, i], bp[side - 1 - i, i]
bl, br = bp[i, side - 1 - i], bp[side - 1 - i, side - 1 - i]
print("corners", tl, tr, bl, br)

def bilinear(size, tl, tr, bl, br):
    out = Image.new("RGB", (size, size))
    o = out.load()
    for y in range(size):
        fy = y / (size - 1)
        for x in range(size):
            fx = x / (size - 1)
            o[x, y] = tuple(
                int(round(tl[c] * (1 - fx) * (1 - fy) + tr[c] * fx * (1 - fy) +
                          bl[c] * (1 - fx) * fy + br[c] * fx * fy)) for c in range(3))
    return out

ADAPT = 432                      # 108dp at xxxhdpi
bg = bilinear(ADAPT, tl, tr, bl, br)

# --- 3. foreground: the badge inset into the safe area ------------------------
INSET = 0.68
inner = int(ADAPT * INSET)
fg = Image.new("RGBA", (ADAPT, ADAPT), (0, 0, 0, 0))
fg.paste(badge.resize((inner, inner), Image.LANCZOS), ((ADAPT - inner) // 2,) * 2)

nod = os.path.join(RES, "drawable-nodpi")
os.makedirs(nod, exist_ok=True)
bg.save(os.path.join(nod, "ic_launcher_art_bg.png"), optimize=True)
fg.save(os.path.join(nod, "ic_launcher_art_fg.png"), optimize=True)

# --- 4. legacy mipmaps, full bleed, square and round -------------------------
for folder, dp in (("mipmap-mdpi", 48), ("mipmap-hdpi", 72), ("mipmap-xhdpi", 96),
                   ("mipmap-xxhdpi", 144), ("mipmap-xxxhdpi", 192)):
    d = os.path.join(RES, folder)
    os.makedirs(d, exist_ok=True)
    flat = badge.resize((dp, dp), Image.LANCZOS)
    flat.save(os.path.join(d, "ic_launcher.png"), optimize=True)
    mask = Image.new("L", (dp * 4, dp * 4), 0)
    ImageDraw.Draw(mask).ellipse((0, 0, dp * 4 - 1, dp * 4 - 1), fill=255)
    rnd = flat.convert("RGBA")
    rnd.putalpha(mask.resize((dp, dp), Image.LANCZOS))
    rnd.save(os.path.join(d, "ic_launcher_round.png"), optimize=True)

print("written")
