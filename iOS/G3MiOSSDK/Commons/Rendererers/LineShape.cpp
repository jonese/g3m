//
//  LineShape.cpp
//  G3MiOSSDK
//
//  Created by Agustín Trujillo on 26/11/13.
//
//

#include "LineShape.hpp"

#include "FloatBufferBuilderFromCartesian3D.hpp"
#include "GLConstants.hpp"
#include "CompositeMesh.hpp"
#include "DirectMesh.hpp"
#include "OrientedBox.hpp"
#include "Camera.hpp"
#include "Vector3D.hpp"


LineShape::~LineShape() {
  delete _color;
  delete _originalColor;
  if (_boundingVolume)
    delete _boundingVolume;
  if (_cartesianPosition)
    delete _cartesianPosition;
  
#ifdef JAVA_CODE
  super.dispose();
#endif
  
}


Mesh* LineShape::createMesh(const G3MRenderContext* rc) {
  FloatBufferBuilderFromCartesian3D* vertices = FloatBufferBuilderFromCartesian3D::builderWithoutCenter();
  
  vertices->add(0.0f, 0.0f, 0.0f);
  vertices->add(0.0f, 0.0f, 5000.0f);
  Color* color = (_color == NULL) ? NULL : new Color(*_color);
  
  Mesh* mesh = new DirectMesh(GLPrimitive::lines(),
                              true,
                              vertices->getCenter(),
                              vertices->create(),
                              _width,
                              1,
                              color);
  
  delete vertices;
  return mesh;
}


std::vector<double> LineShape::intersectionsDistances(const Planet* planet,
                                                      const Camera* camera,
                                                      const Vector3D& origin,
                                                      const Vector3D& direction) {
  if (_boundingVolume != NULL)
    delete _boundingVolume;
  _boundingVolume = computeOrientedBox(planet, camera);
  return _boundingVolume->intersectionsDistances(origin, direction);
}


bool LineShape::isVisible(const G3MRenderContext *rc)
{
  return true;
  //return getBoundingVolume(rc)->touchesFrustum(rc->getCurrentCamera()->getFrustumInModelCoordinates());
}


OrientedBox* LineShape::computeOrientedBox(const Planet* planet,
                                           const Camera* camera)
{
  if (_cartesianPosition == NULL)
    _cartesianPosition = new Vector3D(planet->toCartesian(getPosition()));
  double distanceToCamera = camera->getCartesianPosition().distanceTo(*_cartesianPosition);
  FrustumData frustum = camera->getFrustumData();
  const int pixelWidth = 6*2;
  double scale = 2 * pixelWidth * distanceToCamera * frustum._top / camera->getHeight() / frustum._znear;
  const Vector3D upper = Vector3D(scale, scale, 10*scale);
  const Vector3D lower = Vector3D(-scale, -scale, 0);
  return new OrientedBox(lower, upper, *getTransformMatrix(planet));
}


BoundingVolume* LineShape::getBoundingVolume(const G3MRenderContext *rc)
{
  if (_boundingVolume != NULL)
    delete _boundingVolume;
  _boundingVolume = computeOrientedBox(rc->getPlanet(), rc->getCurrentCamera());
  return _boundingVolume;
}
