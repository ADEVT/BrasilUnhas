package br.adevt.brasilunhas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
	public final static String APP_PATH_SD_CARD = "/BrasilUnhas/";
	public final static String APP_THUMBNAIL_PATH_SD_CARD = "imagens";
	String telaAlvo = "principal";
	LinearLayout layoutPrincipal;
	LinearLayout layoutCategorias;
	LinearLayout layoutSubcategorias;
	LinearLayout layoutMiniaturas;
	RelativeLayout layoutViewPager;
	Button btnCompartilhar, btnSalvar;

	int posicaoPW = 0;

	ArrayList<Bitmap> imagensGridView = new ArrayList<>();

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

		Integer[] imagens = { R.drawable.categorias, R.drawable.facebookcompartilhar, R.drawable.sobre, R.drawable.app };
		menuPrincipal.setAdapter(new AdaptadorMenu(Principal.this, imagens));

		menuPrincipal.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {

				switch (posicao) {
				case 0:
					Integer[] imagens2 = { R.drawable.cores, R.drawable.passoapasso, R.drawable.glitter, R.drawable.francesinha, R.drawable.casamento, R.drawable.animais, R.drawable.desenhos,
							R.drawable.jogos, R.drawable.tresd, R.drawable.paises, R.drawable.times, R.drawable.balada, R.drawable.copadomundo, R.drawable.anonovo, R.drawable.carnaval,
							R.drawable.natal, R.drawable.decoradas, R.drawable.filhaunica, R.drawable.flores, R.drawable.coracoes, R.drawable.nomes };
					categorias.setAdapter(new AdaptadorMenu(Principal.this, imagens2));
					layoutPrincipal.setVisibility(View.GONE);
					layoutCategorias.setVisibility(View.VISIBLE);
					layoutSubcategorias.setVisibility(View.GONE);
					telaAlvo = "categorias";
					break;
				case 1:
					Toast.makeText(Principal.this, "Compartilhando o APP", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(Principal.this, "Sobre o APP", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://search?q=ADEVT"));
					startActivity(intent);
					break;
				}
			}
		});

		categorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				ArrayList<Integer> imagens = new ArrayList<>();
				String categoriaAux = "miniaturas";
				String categoriaSelecionada = "";

				switch (posicao) {
				case 0:
					imagens.add(R.drawable.azul);
					imagens.add(R.drawable.verde);
					imagens.add(R.drawable.vermelho);
					imagens.add(R.drawable.roxo);
					categoriaAux = "subcategoria1";

					break;
				case 1:
					categoriaSelecionada = "passoapasso";
					break;
				case 2:
					categoriaSelecionada = "glitter";
					break;
				case 3:
					categoriaSelecionada = "francesinha";
					break;
				case 4:
					categoriaSelecionada = "casamento";
					break;
				case 5:
					categoriaSelecionada = "animais";
					break;
				case 6:
					categoriaSelecionada = "desenhos";
					break;
				case 7:
					categoriaSelecionada = "jogos";
					break;
				case 8:
					categoriaSelecionada = "3d";
					break;
				case 9:
					categoriaSelecionada = "paises";
					break;
				case 10:
					categoriaSelecionada = "times";
					break;
				case 11:
					categoriaSelecionada = "balada";
					break;
				case 12:
					categoriaSelecionada = "copadomundo";
					break;
				case 13:
					categoriaSelecionada = "anonovo";
					break;
				case 14:
					categoriaSelecionada = "carnaval";
					break;
				case 15:
					categoriaSelecionada = "natal";
					break;
				case 16:
					categoriaSelecionada = "decoradas";
					break;
				case 17:
					categoriaSelecionada = "filhaunica";
					break;
				case 18:
					categoriaSelecionada = "flores";
					break;
				case 19:
					categoriaSelecionada = "coracao";
					break;
				case 20:
					categoriaSelecionada = "nome";
					break;
				}
				// Toast.makeText(Principal.this, categoriaSelecionada,
				// Toast.LENGTH_SHORT).show();
				carregarImagens(categoriaSelecionada, miniaturas);
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);

				if (categoriaAux.equals("subcategoria1")) {
					subcategorias.setAdapter(new AdaptadorMenu(Principal.this, imagens.toArray(new Integer[imagens.size()])));
					layoutCategorias.setVisibility(View.GONE);
					layoutSubcategorias.setVisibility(View.VISIBLE);
				} else {
					miniaturas.setAdapter(new AdaptadorMiniaturas(Principal.this, imagensGridView));
					layoutSubcategorias.setVisibility(View.GONE);
					layoutMiniaturas.setVisibility(View.VISIBLE);
				}
				telaAlvo = "miniaturas";
			}
		});
		subcategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				miniaturas.setAdapter(new AdaptadorMiniaturas(Principal.this, imagensGridView));
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSubcategorias.setVisibility(View.GONE);
				layoutMiniaturas.setVisibility(View.VISIBLE);
				telaAlvo = "subcategoria1";
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
				final Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "url da imagem");

				try {
					startActivity(Intent.createChooser(intent, "Select an action"));
				} catch (android.content.ActivityNotFoundException ex) {
				}
			}
		});

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveImageToExternalStorage(imagensGridView.get(posicaoPW));
			}
		});

	}

	private void carregarImagens(String categoria, GridView miniaturas) {
		String[] urls = { "http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg",
				"http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg",
				"http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg",
				"http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg",
				"http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg" };
		new CarregaImagem(urls, miniaturas).execute();
	}

	class CarregaImagem extends AsyncTask<String, String, String> {
		String[] urls;
		GridView miniaturas;

		public CarregaImagem(String[] urls, GridView miniaturas1) {
			this.urls = urls;
			this.miniaturas = miniaturas1;
		}

		public void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			imagensGridView.clear();
			try {
				for (int i = 0; i < urls.length; i++) {
					URL aURL = new URL(urls[i]);
					HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
					InputStream is = conn.getInputStream();
					Bitmap d = BitmapFactory.decodeStream(is);
					imagensGridView.add(d);
					is.close();
				}
				// return d;
				Toast.makeText(Principal.this, "foi", Toast.LENGTH_SHORT).show();
				return null;
			} catch (Exception e) {
				System.out.println("Exc=" + e);
				return null;
			}

		}

		public void onPostExecute(String a) {
			miniaturas.setAdapter(new AdaptadorMiniaturas(Principal.this, imagensGridView));
		}

	}

	public boolean saveImageToExternalStorage(Bitmap image) {
		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

		try {
			File dir = new File(fullPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			OutputStream fOut = null;
			File file = new File(fullPath, "desiredFilename.png");
			file.createNewFile();
			fOut = new FileOutputStream(file);

			// 100 means no compression, the lower you go, the stronger the
			// compression
			image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

			MediaStore.Images.Media.insertImage(Principal.this.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

			return true;

		} catch (Exception e) {
			// Log.e("saveToExternalStorage()", e.getMessage());
			return false;
		}

	}

	public void onBackPressed() {
		switch (telaAlvo) {
		case "principal":
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Sair?").setMessage("Você deseja realmente sair do aplicativo?")
					.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Principal.this.finish();
						}
					}).setNegativeButton("Não", null).show();
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
		case "subcategoria1":
			layoutPrincipal.setVisibility(View.GONE);
			layoutSubcategorias.setVisibility(View.VISIBLE);
			layoutCategorias.setVisibility(View.GONE);
			layoutMiniaturas.setVisibility(View.GONE);
			layoutViewPager.setVisibility(View.GONE);
			telaAlvo = "subcategoria";
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

}
