//
//  GPUProgram.h
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 27/03/13.
//
//

#ifndef __G3MiOSSDK__GPUProgram__
#define __G3MiOSSDK__GPUProgram__

#include <iostream>
#include <string>
#include <map>

class GPUAttribute;
class GPUUniform;

class GPUUniformBool;
class GPUUniformVec2Float;
class GPUUniformVec4Float;
class GPUUniformFloat;
class GPUUniformMatrix4Float;
class GPUAttributeVec1Float;
class GPUAttributeVec2Float;
class GPUAttributeVec3Float;
class GPUAttributeVec4Float;



class IFloatBuffer;

class GL;

enum ShaderType {
  VERTEX_SHADER,
  FRAGMENT_SHADER
};

class GPUProgram{
  
  //INativeGL* _nativeGL;
  int _programID;
  bool _programCreated;
  std::map<int, GPUAttribute*> _attributes;
  std::map<int, GPUUniform*> _uniforms;
  std::string _name;
  
  bool compileShader(GL* gl, int shader, const std::string& source) const;
  bool linkProgram(GL* gl) const;
  void deleteShader(GL* gl, int shader) const;
  
  void getVariables(GL* gl);
  
  GPUProgram(){}
  
  
public:
  
  static GPUProgram* createProgram(GL* gl, const std::string name, const std::string& vertexSource,
                                   const std::string& fragmentSource);
  
  ~GPUProgram();
  
  std::string getName() const { return _name;}
  
  int getProgramID() const{ return _programID;}
  bool isCreated() const{ return _programCreated;}
  void deleteProgram(GL* gl, int p);
  
  int getGPUAttributesNumber() const { return _attributes.size();}
  int getGPUUniformsNumber() const { return _uniforms.size();}
  
  
  GPUUniform* getGPUUniform(const std::string name) const;
  GPUAttribute* getGPUAttribute(const std::string name) const;
  
  GPUUniformBool* getGPUUniformBool(const std::string name) const;
  GPUUniformVec2Float* getGPUUniformVec2Float(const std::string name) const;
  GPUUniformVec4Float* getGPUUniformVec4Float(const std::string name) const;
  GPUUniformFloat* getGPUUniformFloat(const std::string name) const;
  GPUUniformMatrix4Float* getGPUUniformMatrix4Float(const std::string name) const;
  
  GPUAttribute* getGPUAttributeVecXFloat(const std::string name, int x) const;
  GPUAttributeVec1Float* getGPUAttributeVec1Float(const std::string name) const;
  GPUAttributeVec2Float* getGPUAttributeVec2Float(const std::string name) const;
  GPUAttributeVec3Float* getGPUAttributeVec3Float(const std::string name) const;
  GPUAttributeVec4Float* getGPUAttributeVec4Float(const std::string name) const;
  
  void onUsed();
  void onUnused(GL* gl);
  void applyChanges(GL* gl);
  
  GPUUniform* getUniformOfType(const std::string& name, int type) const;
  
  GPUUniform* getGPUUniform(int key) const;
  GPUAttribute* getGPUAttribute(int key) const;
  GPUAttribute* getGPUAttributeVecXFloat(int key, int x) const;
};

#endif /* defined(__G3MiOSSDK__GPUProgram__) */