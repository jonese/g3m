package org.glob3.mobile.generated; 
//
//  GL.cpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 01/08/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

//
//  GL.hpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 01/08/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//





public class GL extends IGL
{

  private final INativeGL _gl; //NATIVE GL

  private MutableMatrix44D _modelView = new MutableMatrix44D();

  // stack of ModelView matrices
  private java.util.LinkedList<MutableMatrix44D> _matrixStack = new java.util.LinkedList<MutableMatrix44D>();

  // state handling
  private boolean _enableTextures;
  private boolean _enableTexture2D;
  private boolean _enableVertexColor;
  private boolean _enableVertexNormal;
  private boolean _enableVerticesPosition;
  private boolean _enableFlatColor;
  private boolean _enableDepthTest;
  private boolean _enableBlend;

  private boolean _enableCullFace;
  private CullFace _cullFace_face = new CullFace();

  private void loadModelView()
  {
	float[] M = new float[16];
	_modelView.copyToFloatMatrix(M);
	_gl.uniformMatrix4fv(GlobalMembersGL.UniformsGL.Modelview, 1, 0, M);
  }


  public GL(INativeGL gl)
  {
	  _gl = gl;
	  _enableTextures = false;
	  _enableTexture2D = false;
	  _enableVertexColor = false;
	  _enableVertexNormal = false;
	  _enableVerticesPosition = false;
	  _enableFlatColor = false;
	  _enableBlend = false;
	  _enableDepthTest = false;
	  _enableCullFace = false;
	  _cullFace_face = BACK;

  }

  public final void enableVerticesPosition()
  {
	if (!_enableVerticesPosition)
	{
	  _gl.enableVertexAttribArray(GlobalMembersGL.AttributesGL.Position);
	  _enableVerticesPosition = true;
	}
  }


  // state handling
  public final void enableTextures()
  {
	if (!_enableTextures)
	{
	  _gl.enableVertexAttribArray(GlobalMembersGL.AttributesGL.TextureCoord);
	  _enableTextures = true;
	}
  }

//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//  void verticesColors(boolean v);

  public final void enableTexture2D()
  {
	if (!_enableTexture2D)
	{
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableTexture, true);
	  _enableTexture2D = true;
	}
  }

  public final void enableVertexFlatColor(float r, float g, float b, float a, float intensity)
  {
	if (!_enableFlatColor)
	{
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableFlatColor, true);
	  _enableFlatColor = true;
	}
	_gl.uniform4f(GlobalMembersGL.UniformsGL.FlatColor, r, g, b, a);
	_gl.uniform1f(GlobalMembersGL.UniformsGL.FlatColorIntensity, intensity);
  }

  public final void disableVertexFlatColor()
  {
	if (_enableFlatColor)
	{
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableFlatColor, false);
	  _enableFlatColor = false;
	}
  }

  public final void disableTexture2D()
  {
	if (_enableTexture2D)
	{
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableTexture, false);
	  _enableTexture2D = false;
	}
  }

  public final void disableVerticesPosition()
  {
	if (_enableVerticesPosition)
	{
	  _gl.disableVertexAttribArray(GlobalMembersGL.AttributesGL.Position);
	  _enableVerticesPosition = false;
	}
  }

  public final void disableTextures()
  {
	if (_enableTextures)
	{
	  _gl.disableVertexAttribArray(GlobalMembersGL.AttributesGL.TextureCoord);
	  _enableTextures = false;
	}
  }

  public final void clearScreen(float r, float g, float b, float a)
  {
	_gl.clearColor(r, g, b, a);
  
	GLBufferType[] buffers = { ColorBuffer, DepthBuffer };
	_gl.clear(2, buffers);
  }

  public final void color(float r, float g, float b, float a)
  {
	_gl.uniform4f(GlobalMembersGL.UniformsGL.FlatColor, r, g, b, a);
  }

  public final void enableVertexColor(float[] colors, float intensity)
  {
	//  if (colors != NULL) {
	if (!_enableVertexColor)
	{
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableColorPerVertex, true);
	  _gl.enableVertexAttribArray(GlobalMembersGL.AttributesGL.Color);
	  _gl.vertexAttribPointer(GlobalMembersGL.AttributesGL.Color, 4, Float, 0, 0, colors);
	  _gl.uniform1f(GlobalMembersGL.UniformsGL.ColorPerVertexIntensity, intensity);
	  _enableVertexColor = true;
	}
	//  }
  }

  public final void disableVertexColor()
  {
	if (_enableVertexColor)
	{
	  _gl.disableVertexAttribArray(GlobalMembersGL.AttributesGL.Color);
	  _gl.uniform1i(GlobalMembersGL.UniformsGL.EnableColorPerVertex, false);
	  _enableVertexColor = false;
	}
  }

  public final void enableVertexNormal(float[] normals)
  {
	//  if (normals != NULL) {
	if (!_enableVertexNormal)
	{
	  _gl.enableVertexAttribArray(GlobalMembersGL.AttributesGL.Normal);
	  _gl.vertexAttribPointer(GlobalMembersGL.AttributesGL.Normal, 3, Float, 0, 0, normals);
	  _enableVertexNormal = true;
	}
	//  }
  }

  public final void disableVertexNormal()
  {
	if (_enableVertexNormal)
	{
	  _gl.disableVertexAttribArray(GlobalMembersGL.AttributesGL.Normal);
	  _enableVertexNormal = false;
	}
  }

  public final void pushMatrix()
  {
	_matrixStack.addLast(_modelView);
  }

  public final void popMatrix()
  {
	_modelView = _matrixStack.getLast();
	_matrixStack.removeLast();
  
	loadModelView();
  }

  public final void loadMatrixf(MutableMatrix44D modelView)
  {
	_modelView = modelView;
  
	loadModelView();
  }

  public final void multMatrixf(MutableMatrix44D m)
  {
	_modelView = _modelView.multiply(m);
  
	loadModelView();
  }

  public final void vertexPointer(int size, int stride, float[] vertex)
  {
	_gl.vertexAttribPointer(GlobalMembersGL.AttributesGL.Position, size, Float, 0, stride, (Object) vertex);
  }

  public final void drawTriangleStrip(int n, int i)
  {
	_gl.drawElements(TriangleStrip, n, Int, i);
  }

  public final void drawLines(int n, int[] i)
  {
	_gl.drawElements(Lines, n, Int, i);
  }

  public final void drawLineLoop(int n, int[] i)
  {
	_gl.drawElements(LineLoop, n, Int, i);
  }

  public final void drawPoints(int n, int[] i)
  {
	_gl.drawElements(Points, n, Int, i);
  }

  public final void setProjection(MutableMatrix44D projection)
  {
	float[] M = new float[16];
	projection.copyToFloatMatrix(M);
	_gl.uniformMatrix4fv(GlobalMembersGL.UniformsGL.Projection, 1, 0, M);
  }

  public final void useProgram(int program)
  {
	// set shaders
	_gl.useProgram(program);
  
	// Extract the handles to attributes
	GlobalMembersGL.AttributesGL.Position = _gl.getAttribLocation(program, "Position");
	GlobalMembersGL.AttributesGL.TextureCoord = _gl.getAttribLocation(program, "TextureCoord");
	GlobalMembersGL.AttributesGL.Color = _gl.getAttribLocation(program, "Color");
	GlobalMembersGL.AttributesGL.Normal = _gl.getAttribLocation(program, "Normal");
  
	// Extract the handles to uniforms
	GlobalMembersGL.UniformsGL.Projection = _gl.getUniformLocation(program, "Projection");
	GlobalMembersGL.UniformsGL.Modelview = _gl.getUniformLocation(program, "Modelview");
	GlobalMembersGL.UniformsGL.Sampler = _gl.getUniformLocation(program, "Sampler");
	GlobalMembersGL.UniformsGL.EnableTexture = _gl.getUniformLocation(program, "EnableTexture");
	GlobalMembersGL.UniformsGL.FlatColor = _gl.getUniformLocation(program, "FlatColor");
	GlobalMembersGL.UniformsGL.TranslationTexCoord = _gl.getUniformLocation(program, "TranslationTexCoord");
	GlobalMembersGL.UniformsGL.ScaleTexCoord = _gl.getUniformLocation(program, "ScaleTexCoord");
	GlobalMembersGL.UniformsGL.PointSize = _gl.getUniformLocation(program, "PointSize");
  
	// default values
	_gl.uniform2f(GlobalMembersGL.UniformsGL.TranslationTexCoord, 0, 0);
	_gl.uniform2f(GlobalMembersGL.UniformsGL.ScaleTexCoord, 1, 1);
	_gl.uniform1f(GlobalMembersGL.UniformsGL.PointSize, (float) 1.0);
  
	//BILLBOARDS
	GlobalMembersGL.UniformsGL.BillBoard = _gl.getUniformLocation(program, "BillBoard");
	GlobalMembersGL.UniformsGL.ViewPortRatio = _gl.getUniformLocation(program, "ViewPortRatio");
	_gl.uniform1i(GlobalMembersGL.UniformsGL.BillBoard, false); //NOT DRAWING BILLBOARD
  
	//FOR FLAT COLOR MIXING
	GlobalMembersGL.UniformsGL.FlatColorIntensity = _gl.getUniformLocation(program, "FlatColorIntensity");
	GlobalMembersGL.UniformsGL.ColorPerVertexIntensity = _gl.getUniformLocation(program, "ColorPerVertexIntensity");
	GlobalMembersGL.UniformsGL.EnableColorPerVertex = _gl.getUniformLocation(program, "EnableColorPerVertex");
	GlobalMembersGL.UniformsGL.EnableFlatColor = _gl.getUniformLocation(program, "EnableFlatColor");
  }

  public final void enablePolygonOffset(float factor, float units)
  {
	_gl.enable(PolygonOffsetFill);
	_gl.polygonOffset(factor, units);
  }

  public final void disablePolygonOffset()
  {
	_gl.disable(PolygonOffsetFill);
  }

  public final void lineWidth(float width)
  {
	_gl.lineWidth(width);
  }

  public final void pointSize(float size)
  {
	_gl.uniform1f(GlobalMembersGL.UniformsGL.PointSize, size);
  }

  public final int getError()
  {
	return _gl.getError();
  }

  public final int uploadTexture(IImage image, int textureWidth, int textureHeight)
  {
  
	byte[] imageData = new byte[textureWidth * textureHeight * 4];
	image.fillWithRGBA(imageData, textureWidth, textureHeight);
  
  
  
	_gl.blendFunc(SrcAlpha, OneMinusSrcAlpha);
	_gl.pixelStorei(Unpack, 1);
  
	java.util.ArrayList<Integer> ts = _gl.genTextures(1);
	int texID = ts.get(0);
  
  
	_gl.bindTexture(Texture2D, texID);
	_gl.texParameteri(Texture2D, MinFilter, Linear);
	_gl.texParameteri(Texture2D, MagFilter, Linear);
	_gl.texParameteri(Texture2D, WrapS, ClampToEdge);
	_gl.texParameteri(Texture2D, WrapT, ClampToEdge);
  
	_gl.texImage2D(Texture2D, 0, RGBA, textureWidth, textureHeight, 0, RGBA, UnsignedByte, imageData);
  
	return texID;
  }

  public final void setTextureCoordinates(int size, int stride, float[] texcoord)
  {
	_gl.vertexAttribPointer(GlobalMembersGL.AttributesGL.TextureCoord, size, Float, 0, stride, (Object) texcoord);
  }

  public final void bindTexture(int n)
  {
	_gl.bindTexture(Texture2D, n);
  }

  public final void enableDepthTest()
  {
	if (!_enableDepthTest)
	{
	  _gl.enable(DepthTest);
	  _enableDepthTest = true;
	}
  }
  public final void disableDepthTest()
  {
	if (_enableDepthTest)
	{
	  _gl.disable(DepthTest);
	  _enableDepthTest = false;
	}
  }

  public final void enableBlend()
  {
	if (!_enableBlend)
	{
	  _gl.enable(Blend);
	  _enableBlend = true;
	}
  }
  public final void disableBlend()
  {
	if (_enableBlend)
	{
	  _gl.disable(Blend);
	  _enableBlend = false;
	}
  
  }

  public final void drawBillBoard(int textureId, Vector3D pos, float viewPortRatio)
  {
	float[] vertex = { (float) pos.x(), (float) pos.y(), (float) pos.z(), (float) pos.x(), (float) pos.y(), (float) pos.z(), (float) pos.x(), (float) pos.y(), (float) pos.z(), (float) pos.x(), (float) pos.y(), (float) pos.z() };
  
	float[] texcoord = { 1, 1, 1, 0, 0, 1, 0, 0 };
  
	_gl.uniform1i(GlobalMembersGL.UniformsGL.BillBoard, true);
  
	_gl.uniform1f(GlobalMembersGL.UniformsGL.ViewPortRatio, viewPortRatio);
  
	disableDepthTest();
  
	enableTexture2D();
	color(1, 1, 1, 1);
  
	bindTexture(textureId);
  
	vertexPointer(3, 0, vertex);
	setTextureCoordinates(2, 0, texcoord);
  
	_gl.drawArrays(TriangleStrip, 0, 4);
  
	enableDepthTest();
  
	_gl.uniform1i(GlobalMembersGL.UniformsGL.BillBoard, false);
  }

  public final void deleteTexture(int glTextureId)
  {
	int[] textures = { glTextureId };
	_gl.deleteTextures(1, textures);
  }

  public final void enableCullFace(CullFace face)
  {
	if (!_enableCullFace)
	{
	  _gl.enable(CullFacing);
	  _enableCullFace = true;
	}
  
	if (_cullFace_face != face)
	{
	  switch (face)
	  {
		case FRONT:
		  _gl.cullFace(FRONT);
		  break;
		case BACK:
		  _gl.cullFace(BACK);
		  break;
		case FRONT_AND_BACK:
		  _gl.cullFace(FRONT_AND_BACK);
		  break;
	  }
  
	  _cullFace_face = face;
	}
  }
  public final void disableCullFace()
  {
	if (_enableCullFace)
	{
	  _gl.disable(CullFacing);
	  _enableCullFace = false;
	}
  }

  public final void transformTexCoords(Vector2D scale, Vector2D translation)
  {
	_gl.uniform2f(GlobalMembersGL.UniformsGL.ScaleTexCoord, (float) scale.x(), (float) scale.y());
	_gl.uniform2f(GlobalMembersGL.UniformsGL.TranslationTexCoord, (float) translation.x(), (float) translation.y());
  }

}