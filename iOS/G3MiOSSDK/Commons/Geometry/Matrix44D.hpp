//
//  Matrix44D.h
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 01/07/13.
//
//

#ifndef __G3MiOSSDK__Matrix44D__
#define __G3MiOSSDK__Matrix44D__

#include "IFloatBuffer.hpp"

#include "RCObject.hpp"

//class Matrix44D;
//
//class Matrix44DListener{
//public:
//  virtual ~Matrix44DListener() {}
//  virtual void onMatrixBeingDeleted(const Matrix44D* m) = 0;
//};

class Matrix44D: public RCObject {

public:

  //_m23 -> row 2, column 3
  const double _m00;
  const double _m01;
  const double _m02;
  const double _m03;
  const double _m10;
  const double _m11;
  const double _m12;
  const double _m13;
  const double _m20;
  const double _m21;
  const double _m22;
  const double _m23;
  const double _m30;
  const double _m31;
  const double _m32;
  const double _m33;

  mutable float*        _columnMajorFloatArray;
  mutable IFloatBuffer* _columnMajorFloatBuffer;

  //  std::vector<Matrix44DListener*> _listeners;

public:

  explicit Matrix44D(const Matrix44D& m);

  Matrix44D(double m00, double m10, double m20, double m30,
            double m01, double m11, double m21, double m31,
            double m02, double m12, double m22, double m32,
            double m03, double m13, double m23, double m33);

  ~Matrix44D();

  Matrix44D* createMultiplication(const Matrix44D &that) const;

#ifdef C_CODE
  float* getColumnMajorFloatArray() const
#else
  float[] getColumnMajorFloatArray() const
#endif
  {
    if (_columnMajorFloatArray == NULL) {
      _columnMajorFloatArray = new float[16];

      _columnMajorFloatArray[ 0] = (float) _m00;
      _columnMajorFloatArray[ 1] = (float) _m10;
      _columnMajorFloatArray[ 2] = (float) _m20;
      _columnMajorFloatArray[ 3] = (float) _m30;

      _columnMajorFloatArray[ 4] = (float) _m01;
      _columnMajorFloatArray[ 5] = (float) _m11;
      _columnMajorFloatArray[ 6] = (float) _m21;
      _columnMajorFloatArray[ 7] = (float) _m31;

      _columnMajorFloatArray[ 8] = (float) _m02;
      _columnMajorFloatArray[ 9] = (float) _m12;
      _columnMajorFloatArray[10] = (float) _m22;
      _columnMajorFloatArray[11] = (float) _m32;

      _columnMajorFloatArray[12] = (float) _m03;
      _columnMajorFloatArray[13] = (float) _m13;
      _columnMajorFloatArray[14] = (float) _m23;
      _columnMajorFloatArray[15] = (float) _m33;
    }

    return _columnMajorFloatArray;
  }

  const IFloatBuffer* getColumnMajorFloatBuffer() const;

  bool isEqualsTo(const Matrix44D& m) const;

  //    void addListener(Matrix44DListener* l){
  //      _listeners.push_back(l);
  //    }
  //
  //    void removeListener(Matrix44DListener* l){
  //      for (std::vector<Matrix44DListener*>::iterator it = _listeners.begin();
  //           it != _listeners.end(); it++) {
  //        if (*it == l){
  //          _listeners.erase(it);
  //          return;
  //        }
  //      }
  //    }

};


class Matrix44DHolder{
  const Matrix44D* _matrix;
public:
  Matrix44DHolder(const Matrix44D* matrix):_matrix(matrix){
    matrix->_retain();
  }

  ~Matrix44DHolder(){
    _matrix->_release();
  }

  void setMatrix(const Matrix44D* matrix){
    if (matrix != _matrix){
      _matrix->_release();
      _matrix = matrix;
      _matrix->_retain();
    }
  }

  const Matrix44D* getMatrix() const{
    return _matrix;
  }
};


#endif /* defined(__G3MiOSSDK__Matrix44D__) */