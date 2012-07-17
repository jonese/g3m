//
//  TileRenderer.h
//  G3MiOSSDK
//
//  Created by Agustín Trujillo Pino on 12/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#ifndef G3MiOSSDK_TileRenderer_h
#define G3MiOSSDK_TileRenderer_h

#include "Renderer.hpp"

class Tile;
class TileTessellator;
class TileTexturizer;

#include "Sector.hpp"
#include <vector>

#include "Tile.hpp"

class TileCacheEntry {
public:
  Tile* _tile;
  long  _timestamp;
  
  TileCacheEntry(Tile* tile,
                 long  timestamp) :
  _tile(tile),
  _timestamp(timestamp)
  {
    
  }
  
  ~TileCacheEntry() {
    if (_tile != NULL) {
      delete _tile;
    }
  }
};


class TilesCache {
private:
  const int                    _maxElements;
  std::vector<TileCacheEntry*> _entries;
  
  long _tsCounter;
  
public:
  TilesCache(int maxElements) :
  _maxElements(maxElements),
  _tsCounter(0)
  {
    
  }
  
  Tile* getTile(const int level,
                const int row, const int column) {
    
    const int entriesSize = _entries.size();
    for (int i = 0; i < entriesSize; i++) {
      TileCacheEntry* entry = _entries[i];
      Tile* tile = entry->_tile;
      
      if ((tile->getLevel()  == level ) &&
          (tile->getRow()    == row   ) &&
          (tile->getColumn() == column)) {
        entry->_timestamp = _tsCounter++;
        
        return tile;
      }
    }
    
    return NULL;
  }
  
  void putTile(Tile* tile) {
    TileCacheEntry* newEntry = new TileCacheEntry(tile, _tsCounter++);
    
    const int entriesSize = _entries.size();
    if (entriesSize < _maxElements) {
      _entries.push_back(newEntry);
    }
    else {
      int loserI = 0;
      TileCacheEntry* loserEntry = _entries[0];
      
      for (int i = 1; i < entriesSize; i++) {
        TileCacheEntry* entry = _entries[i];
        if (entry->_timestamp < loserEntry->_timestamp) {
          loserEntry = entry;
          loserI = i;
        }
      }
      
      _entries[loserI] = newEntry;
      delete loserEntry;
    }
  }
};


class TileParameters {
public:
  const Sector _topSector;
  const int    _splitsByLatitude;
  const int    _splitsByLongitude;
  const int    _topLevel;
  const int    _maxLevel;
  const int    _maxTilesInCache;
  
  TileParameters(const Sector topSector,
                 const int    splitsByLatitude,
                 const int    splitsByLongitude,
                 const int    topLevel,
                 const int    maxLevel,
                 const int    maxTilesInCache) :
  _topSector(topSector),
  _splitsByLatitude(splitsByLatitude),
  _splitsByLongitude(splitsByLongitude),
  _topLevel(topLevel),
  _maxLevel(maxLevel),
  _maxTilesInCache(maxTilesInCache)
  {
    
  }
  
  static TileParameters* createDefault() {
    const int K = 1;
    const int splitsByLatitude = 2 * K;
    const int splitsByLongitude = 4 * K;
    const int topLevel = 0;
    const int maxLevel = 8;
    const int maxTilesInCache = 128;
    
    return new TileParameters(Sector::fullSphere(),
                              splitsByLatitude,
                              splitsByLongitude,
                              topLevel,
                              maxLevel,
                              maxTilesInCache);
  }
};


class TileRenderer: public Renderer {
private:
  const TileTessellator* _tessellator;
  TileTexturizer*  _texturizer;
  const TileParameters*  _parameters;
  TilesCache*            _tilesCache;
  
  std::vector<Tile*>     _topLevelTiles;
  
  void clearTopLevelTiles();
  void createTopLevelTiles(const InitializationContext* ic);
  
public:
  TileRenderer(const TileTessellator* tessellator,
               TileTexturizer*  texturizer,
               const TileParameters* parameters) :
  _tessellator(tessellator),
  _texturizer(texturizer),
  _parameters(parameters),
  _tilesCache(new TilesCache(parameters->_maxTilesInCache))
  {
    
  }
  
  ~TileRenderer();
  
  void initialize(const InitializationContext* ic);  
  
  int render(const RenderContext* rc);
  
  bool onTouchEvent(const TouchEvent* touchEvent) {
    return false;
  }
  
  void onResizeViewportEvent(int width, int height) {
    
  }
  
};


#endif
