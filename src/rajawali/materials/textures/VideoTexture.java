package rajawali.materials.textures;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.view.Surface;

public class VideoTexture extends ATexture {

	private final int GL_TEXTURE_EXTERNAL_OES = 0x8D65;
	private MediaPlayer mMediaPlayer;
	private SurfaceTexture mSurfaceTexture;
	
	public VideoTexture(String textureName, MediaPlayer mediaPlayer)
	{
		super(TextureType.VIDEO_TEXTURE, textureName);
		mMediaPlayer = mediaPlayer;
		setGLTextureType(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
	}

	public VideoTexture(VideoTexture other)
	{
		super(other);
	}

	public VideoTexture clone() {
		return new VideoTexture(this);
	}

	void add() throws TextureException {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		int textureId = textures[0];
		GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, textureId);
		GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
				GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
				GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		setTextureId(textureId);
		mSurfaceTexture = new SurfaceTexture(textureId);
		mMediaPlayer.setSurface(new Surface(mSurfaceTexture));
	}

	void remove() throws TextureException {
		GLES20.glDeleteTextures(1, new int[] { mTextureId }, 0);
		mSurfaceTexture.release();
	}

	void replace() throws TextureException {
		return;
	}

	void reset() throws TextureException {
		mSurfaceTexture.release();
	}
	
	public void update()
	{
		if(mSurfaceTexture != null)
			mSurfaceTexture.updateTexImage();
	}
}
