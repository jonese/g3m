package org.glob3.mobile.generated; 
public class MapBoo_Scene
{
  private final String _name;
  private final String _description;
  private final MapBoo_MultiImage _screenshot;
  private final Color _backgroundColor ;
  private Layer _baseLayer;
  private Layer _overlayLayer;

  public MapBoo_Scene(String name, String description, MapBoo_MultiImage screenshot, Color backgroundColor, Layer baseLayer, Layer overlayLayer)
  {
     _name = name;
     _description = description;
     _screenshot = screenshot;
     _backgroundColor = new Color(backgroundColor);
     _baseLayer = baseLayer;
     _overlayLayer = overlayLayer;
  }

  public final String getName()
  {
    return _name;
  }

  public final String getDescription()
  {
    return _description;
  }

  public final MapBoo_MultiImage getScreenshot()
  {
    return _screenshot;
  }

  public final Color getBackgroundColor()
  {
    return _backgroundColor;
  }

  public final void fillLayerSet(LayerSet layerSet)
  {
    if (_baseLayer != null)
    {
      layerSet.addLayer(_baseLayer);
    }
  
    if (_overlayLayer != null)
    {
      layerSet.addLayer(_overlayLayer);
    }
  }

  public void dispose()
  {
    if (_screenshot != null)
       _screenshot.dispose();
    if (_baseLayer != null)
       _baseLayer.dispose();
    if (_overlayLayer != null)
       _overlayLayer.dispose();
  }

  public final String description()
  {
    IStringBuilder isb = IStringBuilder.newStringBuilder();
  
    isb.addString("[Scene name=");
    isb.addString(_name);
  
    isb.addString(", description=");
    isb.addString(_description);
  
    isb.addString(", screenshot=");
    isb.addString((_screenshot == null) ? "null" : _screenshot.description());
  
    isb.addString(", backgroundColor=");
    isb.addString(_backgroundColor.description());
  
    isb.addString(", baseLayer=");
    if (_baseLayer == null)
    {
      isb.addString("NULL");
    }
    else
    {
      isb.addString(_baseLayer.description());
    }
  
    isb.addString(", overlayLayer=");
    if (_overlayLayer == null)
    {
      isb.addString("NULL");
    }
    else
    {
      isb.addString(_overlayLayer.description());
    }
  
    isb.addString("]");
  
    final String s = isb.getString();
    if (isb != null)
       isb.dispose();
    return s;
  }

}