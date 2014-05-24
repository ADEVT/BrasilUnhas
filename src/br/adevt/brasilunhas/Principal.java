package br.adevt.brasilunhas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class Principal extends Activity {
	private AdView adView;
	private final String AD_UNIT_ID = "ca-app-pub-4698331571681120/9045635697";
	String telaAlvo = "principal";
	LinearLayout layoutPrincipal;
	LinearLayout layoutCategorias;
	LinearLayout layoutSubcategorias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tela_principal);

		adView = new AdView(this);
		adView.setAdUnitId(AD_UNIT_ID);
		adView.setAdSize(AdSize.SMART_BANNER);

		LinearLayout layoutBanner = (LinearLayout) findViewById(R.id.banner);

		layoutBanner.addView(adView);

		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

		adView.loadAd(adRequest);

		layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal);
		layoutCategorias = (LinearLayout) findViewById(R.id.layout_categorias);
		layoutSubcategorias = (LinearLayout) findViewById(R.id.layout_subcategorias);

		final GridView menuPrincipal = (GridView) findViewById(R.id.gvPrincipal);
		final GridView categorias = (GridView) findViewById(R.id.gvCategorias);
		final GridView subcategorias = (GridView) findViewById(R.id.gvSubcategorias);

		Integer[] imagens = { R.drawable.categorias };
		menuPrincipal.setAdapter(new AdaptadorMenu(Principal.this, imagens));

		menuPrincipal.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
				Integer[] imagens2 = { R.drawable.seta, R.drawable.seta };
				categorias.setAdapter(new AdaptadorMenu(Principal.this, imagens2));
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.VISIBLE);
				telaAlvo = "categorias";
			}
		});

		categorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				ArrayList<Integer> imagens = new ArrayList<>();

				switch (posicao) {
				case 0:
					for (int i = 0; i < 10; i++) {
						imagens.add(R.drawable.seta);
					}
					break;
				}
				subcategorias.setAdapter(new AdaptadorMenu(Principal.this, imagens.toArray(new Integer[imagens.size()])));
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.VISIBLE);
				telaAlvo = "subcategoria";
			}

		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (telaAlvo) {
			case "principal":
				finish();
				break;
			case "categorias":
				layoutPrincipal.setVisibility(View.VISIBLE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				telaAlvo = "principal";
				break;
			case "subcategoria":
				layoutPrincipal.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.VISIBLE);
				telaAlvo = "categorias";
				break;				
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	class AdaptadorMenu extends BaseAdapter {
		private Context ctx;
		private Integer[] img;

		public AdaptadorMenu(Context c, Integer[] imagens) {
			this.ctx = c;
			this.img = imagens;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return img.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
				imagem.setPadding(8, 8, 8, 8);
			} else {
				imagem = (ImageView) convertView;
			}
			imagem.setImageResource(img[posicao]);
			return imagem;
		}

	}
}
