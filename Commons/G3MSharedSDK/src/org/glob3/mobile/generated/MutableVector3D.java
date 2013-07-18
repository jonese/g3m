package org.glob3.mobile.generated; 
//
//  MutableVector3D.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//

//
//  MutableVector3D.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//




//class Vector3D;

public class MutableVector3D
{
  private double _x;
  private double _y;
  private double _z;


  public MutableVector3D()
  {
     _x = 0;
     _y = 0;
     _z = 0;
  }

  public MutableVector3D(double x, double y, double z)
  {
     _x = x;
     _y = y;
     _z = z;

  }

  public MutableVector3D(MutableVector3D v)
  {
     _x = v._x;
     _y = v._y;
     _z = v._z;

  }

  public final MutableVector3D normalized()
  {
    final double d = length();
    return new MutableVector3D(_x / d, _y /d, _z / d);
  }

  public static MutableVector3D nan()
  {
    final IMathUtils mu = IMathUtils.instance();

    return new MutableVector3D(mu.NanD(), mu.NanD(), mu.NanD());
  }

  public final boolean equalTo(MutableVector3D v)
  {
    return (v._x == _x && v._y == _y && v._z == _z);
  }

  public final boolean isNan()
  {
    final IMathUtils mu = IMathUtils.instance();

    return (mu.isNan(_x) || mu.isNan(_y) || mu.isNan(_z));
  }

  public final boolean isZero()
  {
    return (_x == 0) && (_y == 0) && (_z == 0);
  }

  public final double length()
  {
    return IMathUtils.instance().sqrt(squaredLength());
  }

  public final double squaredLength()
  {
    return _x * _x + _y * _y + _z * _z;
  }

  public final double dot(MutableVector3D v)
  {
    return _x * v._x + _y * v._y + _z * v._z;
  }

  public final MutableVector3D add(MutableVector3D v)
  {
    return new MutableVector3D(_x + v._x, _y + v._y, _z + v._z);
  }

  public final MutableVector3D sub(MutableVector3D v)
  {
    return new MutableVector3D(_x - v._x, _y - v._y, _z - v._z);
  }

  public final MutableVector3D times(MutableVector3D v)
  {
    return new MutableVector3D(_x * v._x, _y * v._y, _z * v._z);
  }

  public final MutableVector3D times(Vector3D v)
  {
    return new MutableVector3D(_x * v._x, _y * v._y, _z * v._z);
  }

  public final MutableVector3D times(double magnitude)
  {
    return new MutableVector3D(_x * magnitude, _y * magnitude, _z * magnitude);
  }

  public final MutableVector3D div(MutableVector3D v)
  {
    return new MutableVector3D(_x / v._x, _y / v._y, _z / v._z);
  }

  public final MutableVector3D div(double v)
  {
    return new MutableVector3D(_x / v, _y / v, _z / v);
  }

  public final MutableVector3D cross(MutableVector3D other)
  {
    return new MutableVector3D(_y * other._z - _z * other._y, _z * other._x - _x * other._z, _x * other._y - _y * other._x);
  }

  public final Angle angleBetween(MutableVector3D other)
  {
    final MutableVector3D v1 = normalized();
    final MutableVector3D v2 = other.normalized();
  
    double c = v1.dot(v2);
    if (c > 1.0)
       c = 1.0;
    else if (c < -1.0)
       c = -1.0;
  
    return Angle.fromRadians(IMathUtils.instance().acos(c));
  }

  public final MutableVector3D rotatedAroundAxis(MutableVector3D axis, Angle theta)
  {
    final double u = axis.x();
    final double v = axis.y();
    final double w = axis.z();
  
    final double cosTheta = theta.cosinus();
    final double sinTheta = theta.sinus();
  
    final double ms = axis.squaredLength();
    final double m = IMathUtils.instance().sqrt(ms);
  
    return new MutableVector3D(((u * (u * _x + v * _y + w * _z)) + (((_x * (v * v + w * w)) - (u * (v * _y + w * _z))) * cosTheta) + (m * ((-w * _y) + (v * _z)) * sinTheta)) / ms, ((v * (u * _x + v * _y + w * _z)) + (((_y * (u * u + w * w)) - (v * (u * _x + w * _z))) * cosTheta) + (m * ((w * _x) - (u * _z)) * sinTheta)) / ms, ((w * (u * _x + v * _y + w * _z)) + (((_z * (u * u + v * v)) - (w * (u * _x + v * _y))) * cosTheta) + (m * (-(v * _x) + (u * _y)) * sinTheta)) / ms);
  }

  public final double x()
  {
    return _x;
  }

  public final double y()
  {
    return _y;
  }

  public final double z()
  {
    return _z;
  }

  public final MutableVector3D transformedBy(MutableMatrix44D m, double homogeneus)
  {
    return new MutableVector3D(_x * m.get0() + _y * m.get4() + _z * m.get8() + homogeneus * m.get12(), _x * m.get1() + _y * m.get5() + _z * m.get9() + homogeneus * m.get13(), _x * m.get2() + _y * m.get6() + _z * m.get10() + homogeneus * m.get14());
  }

  public final Vector3D asVector3D()
  {
    return new Vector3D(_x, _y, _z);
  }

}