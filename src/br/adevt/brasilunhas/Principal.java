package br.adevt.brasilunhas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
	LinearLayout layoutMiniaturas;
	RelativeLayout layoutViewPager;
	Button btnCompartilhar, btnSalvar;

	int posicaoPW = 0;

	Integer[] imagensGridView = { R.drawable.foto1, R.drawable.foto3, R.drawable.foto4, R.drawable.foto1 };

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

		btnCompartilhar = (Button) findViewById(R.id.btnCompartilhar);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);

		layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal);
		layoutCategorias = (LinearLayout) findViewById(R.id.layout_categorias);
		layoutSubcategorias = (LinearLayout) findViewById(R.id.layout_subcategorias);
		layoutMiniaturas = (LinearLayout) findViewById(R.id.layout_miniaturas);
		layoutViewPager = (RelativeLayout) findViewById(R.id.layout_viewPager);

		final GridView menuPrincipal = (GridView) findViewById(R.id.gvPrincipal);
		final GridView categorias = (GridView) findViewById(R.id.gvCategorias);
		final GridView subcategorias = (GridView) findViewById(R.id.gvSubcategorias);
		final GridView miniaturas = (GridView) findViewById(R.id.gvMiniaturas);
		final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

		Integer[] imagens = { R.drawable.categorias };
		menuPrincipal.setAdapter(new AdaptadorMenu(Principal.this, imagens));

		menuPrincipal.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
				Integer[] imagens2 = { R.drawable.baixar, R.drawable.baixar, R.drawable.baixar, R.drawable.baixar };
				categorias.setAdapter(new AdaptadorMenu(Principal.this, imagens2));
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.VISIBLE);
				layoutSubcategorias.setVisibility(View.GONE);
				telaAlvo = "categorias";
			}
		});

		categorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				ArrayList<Integer> imagens = new ArrayList<>();

				switch (posicao) {
				case 3:
					imagens.add(R.drawable.azul);
					imagens.add(R.drawable.verde);
					imagens.add(R.drawable.vermelho);
					imagens.add(R.drawable.roxo);

					break;
				}
				subcategorias.setAdapter(new AdaptadorMenu(Principal.this, imagens.toArray(new Integer[imagens.size()])));

				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.VISIBLE);
				telaAlvo = "subcategoria";
			}

		});
		subcategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				miniaturas.setAdapter(new AdaptadorMenu(Principal.this, imagensGridView));
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutMiniaturas.setVisibility(View.VISIBLE);
				telaAlvo = "miniaturas";
			}

		});

		miniaturas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				viewPager.setAdapter(new AdaptadorImagem_ViewPager(Principal.this, imagensGridView));
				viewPager.setCurrentItem(posicao);
				posicaoPW = posicao;
				viewPager.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int posicao) {
						posicaoPW = posicao;
						Toast.makeText(Principal.this, "posição PW: " + posicaoPW, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutMiniaturas.setVisibility(View.GONE);
				layoutViewPager.setVisibility(View.VISIBLE);
				telaAlvo = "viewpager";
			}
		});

		btnCompartilhar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(Principal.this, "Compartilhando a imagem: " + posicaoPW, Toast.LENGTH_SHORT).show();
				System.out.println("AQUI >>>>>>>>>>>>>>> " + posicaoPW);

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
				layoutMiniaturas.setVisibility(View.GONE);
				layoutViewPager.setVisibility(View.GONE);
				telaAlvo = "principal";
				break;
			case "subcategoria":
				layoutPrincipal.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.VISIBLE);
				layoutMiniaturas.setVisibility(View.GONE);
				layoutViewPager.setVisibility(View.GONE);
				telaAlvo = "categorias";
				break;
			case "miniaturas":
				layoutPrincipal.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.VISIBLE);
				layoutMiniaturas.setVisibility(View.GONE);
				layoutViewPager.setVisibility(View.GONE);
				telaAlvo = "categorias";
				break;
			case "viewpager":
				layoutPrincipal.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutMiniaturas.setVisibility(View.VISIBLE);
				layoutViewPager.setVisibility(View.GONE);
				telaAlvo = "miniaturas";
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
				imagem.setPadding(2, 2, 2, 2);
			} else {
				imagem = (ImageView) convertView;
			}
			imagem.setImageResource(img[posicao]);
			return imagem;
		}

	}

	public class AdaptadorImagem_ViewPager extends PagerAdapter {
		private Context ctx;
		private final Integer[] imagens;

		public AdaptadorImagem_ViewPager(Context c, Integer[] imagens) {
			this.ctx = c;
			this.imagens = imagens;
		}

		@Override
		public int getCount() {
			return imagens.length;
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
			img.setImageResource(imagens[position]);
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
}
