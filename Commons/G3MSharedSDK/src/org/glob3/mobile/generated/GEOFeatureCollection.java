package org.glob3.mobile.generated; 
//
//  GEOFeatureCollection.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/29/12.
//
//

//
//  GEOFeatureCollection.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/29/12.
//
//




//class GEOFeature;
//class GPUProgramState;
//class GLGlobalState;
//class GPUProgramState;
//class GEOSymbolizer;

public class GEOFeatureCollection extends GEOObject
{
  private java.util.ArrayList<GEOFeature> _features = new java.util.ArrayList<GEOFeature>();

  public GEOFeatureCollection(java.util.ArrayList<GEOFeature> features)
  {
     _features = features;
  }

  public void dispose()
  {
    final int featuresCount = _features.size();
    for (int i = 0; i < featuresCount; i++)
    {
      GEOFeature feature = _features.get(i);
      if (feature != null)
         feature.dispose();
    }
  
    super.dispose();
  
  }

  public final void symbolize(G3MRenderContext rc, GEOSymbolizer symbolizer, MeshRenderer meshRenderer, ShapesRenderer shapesRenderer, MarksRenderer marksRenderer, GEOTileRasterizer geoTileRasterizer)
  {
    final int featuresCount = _features.size();
    for (int i = 0; i < featuresCount; i++)
    {
      GEOFeature feature = _features.get(i);
      feature.symbolize(rc, symbolizer, meshRenderer, shapesRenderer, marksRenderer, geoTileRasterizer);
    }
  }

  public final GEOFeature get(int i)
  {
     return _features.get(i);
  }
  public final int size()
  {
     return _features.size();
  }

}