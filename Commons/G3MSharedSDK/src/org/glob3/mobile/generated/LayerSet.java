package org.glob3.mobile.generated; 
//
//  LayerSet.cpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 23/07/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

//
//  LayerSet.hpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 23/07/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//



//class Petition;
//class Vector2I;
//class LayerTilesRenderParameters;

//class ChangedListener;

public class LayerSet
{
  private java.util.ArrayList<Layer> _layers = new java.util.ArrayList<Layer>();

  private ChangedListener _listener;

//  mutable LayerTilesRenderParameters* _layerTilesRenderParameters;
  private java.util.ArrayList<String> _errors = new java.util.ArrayList<String>();

  private void layersChanged()
  {
  //  delete _layerTilesRenderParameters;
  //  _layerTilesRenderParameters = NULL;
  
    if (_listener != null)
    {
      _listener.changed();
    }
  }

  private G3MContext _context;

  public LayerSet()
//  _layerTilesRenderParameters(NULL),
  {
     _listener = null;
     _context = null;

  }

  public void dispose()
  {
  //  delete _layerTilesRenderParameters;
    for (int i = 0; i < _layers.size(); i++)
    {
      if (_layers.get(i) != null)
         _layers.get(i).dispose();
    }
  }

  public final void removeAllLayers(boolean deleteLayers)
  {
    final int layersSize = _layers.size();
    if (layersSize > 0)
    {
      for (int i = 0; i < layersSize; i++)
      {
        Layer layer = _layers.get(i);
        layer.removeLayerSet(this);
        if (deleteLayers)
        {
          if (layer != null)
             layer.dispose();
        }
      }
      _layers.clear();
  
      layersChanged();
    }
  }

  public final void addLayer(Layer layer)
  {
    layer.setLayerSet(this);
    _layers.add(layer);
  
    if (_context != null)
    {
      layer.initialize(_context);
    }
  
    layersChanged();
  }

  public final java.util.ArrayList<Petition> createTileMapPetitions(G3MRenderContext rc, LayerTilesRenderParameters layerTilesRenderParameters, Tile tile)
  {
    java.util.ArrayList<Petition> petitions = new java.util.ArrayList<Petition>();
  
    final int layersSize = _layers.size();
    for (int i = 0; i < layersSize; i++)
    {
      Layer layer = _layers.get(i);
      if (layer.isAvailable(rc, tile))
      {
  
        Tile petitionTile = tile;
        final int maxLevel = layer.getLayerTilesRenderParameters()._maxLevel;
        while ((petitionTile._level > maxLevel) && (petitionTile != null))
        {
          petitionTile = petitionTile.getParent();
        }
  
        if (petitionTile == null)
        {
          ILogger.instance().logError("Can't find a valid tile for petitions");
        }
  
        java.util.ArrayList<Petition> tilePetitions = layer.createTileMapPetitions(rc, layerTilesRenderParameters, petitionTile);
  
        final int tilePetitionsSize = tilePetitions.size();
        for (int j = 0; j < tilePetitionsSize; j++)
        {
          petitions.add(tilePetitions.get(j));
        }
      }
    }
  
    if (petitions.isEmpty())
    {
      rc.getLogger().logWarning("Can't create map petitions for tile %s", tile.getKey().description());
    }
  
    return petitions;
  }

  public final boolean onTerrainTouchEvent(G3MEventContext ec, Geodetic3D position, Tile tile)
  {
  
    for (int i = _layers.size()-1; i >= 0; i--)
    {
      Layer layer = _layers.get(i);
      if (layer.isAvailable(ec, tile))
      {
        LayerTouchEvent tte = new LayerTouchEvent(position, tile._sector, layer);
  
        if (layer.onLayerTouchEventListener(ec, tte))
        {
          return true;
        }
      }
    }
  
    return false;
  }

  public final RenderState getRenderState()
  {
    _errors.clear();
    boolean busyFlag = false;
    boolean errorFlag = false;
    final int layersCount = _layers.size();
  
    for (int i = 0; i < layersCount; i++)
    {
      Layer child = _layers.get(i);
      if (child.isEnable())
      {
        final RenderState childRenderState = child.getRenderState();
  
        final RenderState_Type childRenderStateType = childRenderState._type;
  
        if (childRenderStateType == RenderState_Type.RENDER_ERROR)
        {
          errorFlag = true;
  
          final java.util.ArrayList<String> childErrors = childRenderState.getErrors();
          _errors.addAll(childErrors);
        }
        else if (childRenderStateType == RenderState_Type.RENDER_BUSY)
        {
          busyFlag = true;
        }
      }
    }
  
    if (errorFlag)
    {
      return RenderState.error(_errors);
    }
    else if (busyFlag)
    {
      return RenderState.busy();
    }
    return RenderState.ready();
  }

  public final void initialize(G3MContext context)
  {
    _context = context;
  
    final int layersCount = _layers.size();
    for (int i = 0; i < layersCount; i++)
    {
      _layers.get(i).initialize(context);
    }
  }

  public final int size()
  {
    return _layers.size();
  }

  public final void layerChanged(Layer layer)
  {
    layersChanged();
  }

  public final void setChangeListener(ChangedListener listener)
  {
    if (_listener != null)
    {
      ILogger.instance().logError("Listener already set");
    }
    _listener = listener;
  }

  public final Layer getLayer(int index)
  {
    if (index < _layers.size())
    {
      return _layers.get(index);
    }
  
    return null;
  }

  public final Layer getLayerByName(String name)
  {
    final int layersCount = _layers.size();
    for (int i = 0; i < layersCount; i++)
    {
      if (name.equals(_layers.get(i).getName()))
      {
        return _layers.get(i);
      }
    }
    return null;
  }
  public final Layer getLayerByTitle(String title)
  {
    final int layersCount = _layers.size();
    for (int i = 0; i < layersCount; i++)
    {
      if (title.equals(_layers.get(i).getTitle()))
      {
        return _layers.get(i);
      }
    }
    return null;
  }

  public final LayerTilesRenderParameters createLayerTilesRenderParameters(java.util.ArrayList<String> errors)
  {
    Sector topSector = null;
    int topSectorSplitsByLatitude = 0;
    int topSectorSplitsByLongitude = 0;
    int firstLevel = 0;
    int maxLevel = 0;
    int tileTextureWidth = 0;
    int tileTextureHeight = 0;
    int tileMeshWidth = 0;
    int tileMeshHeight = 0;
    boolean mercator = false;
  
    boolean layerSetNotReadyFlag = false;
    boolean first = true;
    final int layersCount = _layers.size();
    for (int i = 0; i < layersCount; i++)
    {
      Layer layer = _layers.get(i);
  
      if (layer.isEnable())
      {
        final RenderState layerRenderState = layer.getRenderState();
        final RenderState_Type layerRenderStateType = layerRenderState._type;
        if (layerRenderStateType != RenderState_Type.RENDER_READY)
        {
          if (layerRenderStateType == RenderState_Type.RENDER_ERROR)
          {
            final java.util.ArrayList<String> layerErrors = layerRenderState.getErrors();
            errors.addAll(layerErrors);
          }
          layerSetNotReadyFlag = true;
        }
        else
        {
          final LayerTilesRenderParameters layerParam = layer.getLayerTilesRenderParameters();
  
          if (layerParam == null)
          {
            continue;
          }
  
          if (first)
          {
            first = false;
  
            topSector = new Sector(layerParam._topSector);
            topSectorSplitsByLatitude = layerParam._topSectorSplitsByLatitude;
            topSectorSplitsByLongitude = layerParam._topSectorSplitsByLongitude;
            firstLevel = layerParam._firstLevel;
            maxLevel = layerParam._maxLevel;
            tileTextureWidth = layerParam._tileTextureResolution._x;
            tileTextureHeight = layerParam._tileTextureResolution._y;
            tileMeshWidth = layerParam._tileMeshResolution._x;
            tileMeshHeight = layerParam._tileMeshResolution._y;
            mercator = layerParam._mercator;
          }
          else
          {
            if (mercator != layerParam._mercator)
            {
              errors.add("Inconsistency in Layer's Parameters: mercator");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if (!topSector.isEquals(layerParam._topSector))
            {
              errors.add("Inconsistency in Layer's Parameters: topSector");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if (topSectorSplitsByLatitude != layerParam._topSectorSplitsByLatitude)
            {
              errors.add("Inconsistency in Layer's Parameters: topSectorSplitsByLatitude");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if (topSectorSplitsByLongitude != layerParam._topSectorSplitsByLongitude)
            {
              errors.add("Inconsistency in Layer's Parameters: topSectorSplitsByLongitude");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if ((tileTextureWidth != layerParam._tileTextureResolution._x) || (tileTextureHeight != layerParam._tileTextureResolution._y))
            {
              errors.add("Inconsistency in Layer's Parameters: tileTextureResolution");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if ((tileMeshWidth != layerParam._tileMeshResolution._x) || (tileMeshHeight != layerParam._tileMeshResolution._y))
            {
              errors.add("Inconsistency in Layer's Parameters: tileMeshResolution");
              if (topSector != null)
                 topSector.dispose();
              return null;
            }
  
            if (maxLevel < layerParam._maxLevel)
            {
              ILogger.instance().logWarning("Inconsistency in Layer's Parameters: maxLevel (upgrading from %d to %d)", maxLevel, layerParam._maxLevel);
              maxLevel = layerParam._maxLevel;
            }
  
            if (firstLevel < layerParam._firstLevel)
            {
              ILogger.instance().logWarning("Inconsistency in Layer's Parameters: firstLevel (upgrading from %d to %d)", firstLevel, layerParam._firstLevel);
              firstLevel = layerParam._firstLevel;
            }
  
          }
        }
      }
    }
  
    if (layerSetNotReadyFlag)
    {
      return null;
    }
    if (first)
    {
      errors.add("Can't find any enabled Layer");
      return null;
    }
  
    LayerTilesRenderParameters parameters = new LayerTilesRenderParameters(topSector, topSectorSplitsByLatitude, topSectorSplitsByLongitude, firstLevel, maxLevel, new Vector2I(tileTextureWidth, tileTextureHeight), new Vector2I(tileMeshWidth, tileMeshHeight), mercator);
  
    if (topSector != null)
       topSector.dispose();
  
    return parameters;
  }


  //const LayerTilesRenderParameters* LayerSet::getLayerTilesRenderParameters(std::vector<std::string>& errors) const {
  //  if (_layerTilesRenderParameters == NULL) {
  //    _layerTilesRenderParameters = createLayerTilesRenderParameters(errors);
  //  }
  //  return _layerTilesRenderParameters;
  //}
  
  public final boolean isEquals(LayerSet that)
  {
    if (that == null)
    {
      return false;
    }
  
    final int thisSize = size();
    final int thatSize = that.size();
  
    if (thisSize != thatSize)
    {
      return false;
    }
  
    for (int i = 0; i < thisSize; i++)
    {
      Layer thisLayer = getLayer(i);
      Layer thatLayer = that.getLayer(i);
  
      if (!thisLayer.isEquals(thatLayer))
      {
        return false;
      }
    }
  
    return true;
  }

  public final void takeLayersFrom(LayerSet that)
  {
    if (that == null)
    {
      return;
    }
  
    java.util.ArrayList<Layer> thatLayers = new java.util.ArrayList<Layer>();
    final int thatSize = that.size();
    for (int i = 0; i < thatSize; i++)
    {
      thatLayers.add(that.getLayer(i));
    }
  
    that.removeAllLayers(false);
  
    for (int i = 0; i < thatSize; i++)
    {
      addLayer(thatLayers.get(i));
    }
  }

  public final void disableAllLayers()
  {
    final int layersCount = _layers.size();
    for (int i = 0; i < layersCount; i++)
    {
      _layers.get(i).setEnable(false);
    }
  }

}