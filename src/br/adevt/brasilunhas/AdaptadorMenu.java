package br.adevt.brasilunhas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdaptadorMenu extends BaseAdapter {
	private Context ctx;
	private Integer[] img;

	public AdaptadorMenu(Context c, Integer[] imagens) {
		this.ctx = c;
		this.img = imagens;
	}

	@Override
	public int getCount() {
		return img.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		ImageView imagem;

		if (convertView == null) {
			imagem = new ImageView(ctx);
			// imagem.setLayoutParams(new GridView.LayoutParams(120,120));
			imagem.setAdjustViewBounds(true);
			imagem.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imagem.setPadding(2, 2, 2, 2);
		} else {
			imagem = (ImageView) convertView;
		}
		imagem.setImageResource(img[posicao]);
		return imagem;
	}

}