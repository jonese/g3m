//
//  FlatColorMesh.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 23/06/13.
//
//

#include "FlatColorMesh.hpp"

void FlatColorMesh::render(const G3MRenderContext* rc) const {
  _mesh->render(rc);
}

void FlatColorMesh::createGLState(){
  _glState.addGLFeature(new FlatColorGLFeature(*_flatColor,
                                               _flatColor->isTransparent(),
                                               GLBlendFactor::srcAlpha(), GLBlendFactor::oneMinusSrcAlpha()), false);
}

void FlatColorMesh::render(const G3MRenderContext* rc, const GLState* parentState){
  _glState.setParent(parentState);
  _mesh->render(rc, &_glState);
}