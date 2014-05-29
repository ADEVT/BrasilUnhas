package br.adevt.brasilunhas;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdaptadorImagem_ViewPager extends PagerAdapter {
	private Context ctx;
	private final ArrayList<Bitmap> imagens;

	public AdaptadorImagem_ViewPager(Context c, ArrayList<Bitmap> imagens2) {
		this.ctx = c;
		this.imagens = imagens2;
	}

	@Override
	public int getCount() {
		return imagens.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// Determina se a view informada é igual ao object retornado pelo
		// instantiateItem
		return view == ((MeuObjeto) object).view;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView img = new ImageView(ctx);
		img.setImageBitmap(imagens.get(position));
		img.setAdjustViewBounds(true);

		// Adiciona no layout ViewGroup
		((ViewGroup) container).addView(img);

		MeuObjeto obj = new MeuObjeto();
		obj.view = img;
		return obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object obj) {
		// Remove do layout
		MeuObjeto meuObj = (MeuObjeto) obj;
		((ViewPager) container).removeView(meuObj.view);
	}

	// Apenas para demosntrar que você pode retornar um Objeto qualquer que
	// contém a view
	class MeuObjeto {
		View view;
	}
}