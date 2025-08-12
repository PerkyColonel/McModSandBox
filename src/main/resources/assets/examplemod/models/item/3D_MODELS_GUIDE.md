# 3D Item Models Guide

This guide explains how to create and add 3D models for items in your Minecraft Forge mod.

## Directory Structure

```
src/main/resources/assets/examplemod/
├── models/item/
│   ├── 3d/                          # 3D model definitions
│   │   ├── gigantic_bread_3d.json   # Example 3D model
│   │   └── TEMPLATE_3d_item.json    # Template for new models
│   ├── your_item.json               # Item display properties
│   └── TEMPLATE_3d_display.json     # Template for display properties
├── textures/item/
│   └── your_texture.png             # 16x16 or 32x32 texture
```

## Creating a New 3D Item

### Step 1: Create the 3D Model File

1. Copy `TEMPLATE_3d_item.json` to `3d/your_item_3d.json`
2. Update the texture references:
   ```json
   "textures": {
     "0": "examplemod:item/your_texture_name",
     "particle": "examplemod:item/your_texture_name"
   }
   ```
3. Modify the `elements` array to define your 3D shapes

### Step 2: Create the Display Properties File

1. Copy `TEMPLATE_3d_display.json` to `your_item.json`
2. Update the parent reference:
   ```json
   "parent": "examplemod:item/3d/your_item_3d"
   ```
3. Adjust display properties as needed

### Step 3: Add Your Texture

1. Create a 16x16 or 32x32 PNG texture
2. Place it in `textures/item/your_texture_name.png`

## 3D Model Elements Explained

### Element Structure
```json
{
  "name": "element_name",
  "from": [x1, y1, z1],           # Start coordinates (0-16)
  "to": [x2, y2, z2],             # End coordinates (0-16)
  "rotation": {                   # Optional rotation
    "angle": 45,                  # Rotation angle
    "axis": "y",                  # Rotation axis (x, y, z)
    "origin": [8, 8, 8]          # Rotation center point
  },
  "faces": {
    "north": {"uv": [0, 0, 16, 16], "texture": "#0"},
    "south": {"uv": [0, 0, 16, 16], "texture": "#0"},
    "east": {"uv": [0, 0, 16, 16], "texture": "#0"},
    "west": {"uv": [0, 0, 16, 16], "texture": "#0"},
    "up": {"uv": [0, 0, 16, 16], "texture": "#0"},
    "down": {"uv": [0, 0, 16, 16], "texture": "#0"}
  }
}
```

### Display Properties Explained

- **thirdperson_righthand/lefthand**: How item appears when held by player
- **firstperson_righthand/lefthand**: How item appears in first-person view
- **ground**: How item appears when dropped on ground
- **gui**: How item appears in inventory/GUI
- **head**: How item appears when worn on head
- **fixed**: How item appears in item frames

Each display context has:
- **rotation**: `[x, y, z]` rotation in degrees
- **translation**: `[x, y, z]` position offset
- **scale**: `[x, y, z]` size multiplier

## Tools for Creating 3D Models

### Recommended: Blockbench
1. Download Blockbench (free): https://blockbench.net/
2. Choose "Java Block/Item" format
3. Create your model visually
4. Export as JSON
5. Copy the elements array to your model file

### Manual Creation
- Use coordinates from 0-16 for each axis
- Each unit = 1 pixel at normal scale
- Y=0 is bottom, Y=16 is top
- Keep models reasonable in size

## UV Mapping

UV coordinates map texture pixels to model faces:
- `[0, 0, 16, 16]` = entire texture
- `[0, 0, 8, 8]` = top-left quarter
- `[8, 8, 16, 16]` = bottom-right quarter

## Example: Gigantic Bread

The gigantic bread model has 3 elements:
1. **bread_base**: Main loaf body (large)
2. **bread_top**: Slightly smaller top layer
3. **bread_crust**: Small crust detail on top

This creates a layered, 3D bread appearance that's larger than vanilla bread.

## Testing Your Model

1. Build your mod: `./gradlew build`
2. Run the client: `./gradlew runClient`
3. Check the item in:
   - Creative inventory (GUI view)
   - Player hand (first/third person)
   - Ground when dropped
   - Item frame when placed

## Troubleshooting

- **Model not loading**: Check JSON syntax and file paths
- **Texture not showing**: Verify texture path and file exists
- **Model too big/small**: Adjust element coordinates and display scale
- **Wrong rotation**: Modify display rotation values
- **Performance issues**: Keep element count reasonable (under 20)

## Performance Tips

- Use fewer elements for better performance
- Combine simple shapes when possible
- Avoid overly complex models for common items
- Test on slower machines if possible