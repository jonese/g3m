package org.glob3.mobile.generated; 
public class DownloaderImageBuilder_ImageDownloadListener extends IImageDownloadListener
{
  private IImageBuilderListener _listener;
  private final boolean _deleteListener;

  public DownloaderImageBuilder_ImageDownloadListener(IImageBuilderListener listener, boolean deleteListener)
  {
     _listener = listener;
     _deleteListener = deleteListener;
  }

  public void dispose()
  {
    if (_deleteListener)
    {
      _listener = null;
    }
  }

  public final void onDownload(URL url, IImage image, boolean expired)
  {
    _listener.imageCreated(image, url.getPath());
    if (_deleteListener)
    {
      _listener = null;
      _listener = null;
    }
  }

  public final void onError(URL url)
  {
    _listener.onError("Error downloading image from \"" + url.getPath() + "\"");
    if (_deleteListener)
    {
      _listener = null;
      _listener = null;
    }
  }

  public final void onCancel(URL url)
  {
    _listener.onError("Canceled download image from \"" + url.getPath() + "\"");
    if (_deleteListener)
    {
      _listener = null;
      _listener = null;
    }
  }

  public final void onCanceledDownload(URL url, IImage image, boolean expired)
  {
    // do nothing
  }

}