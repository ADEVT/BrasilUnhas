package br.adevt.brasilunhas;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdaptadorMiniaturas extends BaseAdapter {
	private Context ctx;
	private final ArrayList<Bitmap> imagens;

	public AdaptadorMiniaturas(Context c, ArrayList<Bitmap> imagens2) {
		this.ctx = c;
		this.imagens = imagens2;
	}

	public int getCount() {
		return imagens.size();
	}

	public Object getItem(int posicao) {
		return posicao;
	}

	public long getItemId(int posicao) {
		return posicao;
	}

	public View getView(int posicao, View convertView, ViewGroup parent) {
		ImageView img;
		if (convertView == null) {
			img = new ImageView(ctx);
			//img.setLayoutParams(new GridView.LayoutParams(100,100));
			img.setAdjustViewBounds(true);
			img.setScaleType(ImageView.ScaleType.CENTER_CROP);
			img.setPadding(2, 2, 2, 2);
		} else {
			img = (ImageView) convertView;
		}
		img.setImageBitmap(imagens.get(posicao));
		return img;
	}	
}

// http://marciocardoso.net/blog/?p=112
// http://i.stack.imgur.com/xmGxs.png