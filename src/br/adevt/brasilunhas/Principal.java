package br.adevt.brasilunhas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class Principal extends AbsListViewBaseActivity {
	private AdView adView;
	private final String AD_UNIT_ID = "ca-app-pub-4698331571681120/9045635697";
	public final static String APP_PATH_SD_CARD = "/BrasilUnhas/";
	public final static String APP_THUMBNAIL_PATH_SD_CARD = "imagens";
	final String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
	String telaAlvo = "principal";
	LinearLayout layoutPrincipal;
	LinearLayout layoutCategorias;
	LinearLayout layoutSubcategorias;
	LinearLayout layoutMiniaturas;
	RelativeLayout layoutSobre;
	RelativeLayout layoutViewPager;
	Button btnCompartilhar, btnSalvar, btnAvaliarApp;

	ArrayList<String> imageUrls = new ArrayList<>();

	String categoriaSelecionada = "";

	DisplayImageOptions options;
	DisplayImageOptions optionsVP;

	int posicaoPW = 0;

	// ArrayList<Bitmap> imagensGridView = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseActivity.imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tela_principal);

		adView = new AdView(this);
		adView.setAdUnitId(AD_UNIT_ID);
		adView.setAdSize(AdSize.SMART_BANNER);

		LinearLayout layoutBanner = (LinearLayout) findViewById(R.id.banner);

		layoutBanner.addView(adView);

		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

		adView.loadAd(adRequest);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		optionsVP = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();

		btnCompartilhar = (Button) findViewById(R.id.btnCompartilhar);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnAvaliarApp = (Button) findViewById(R.id.btnAvaliarApp);

		layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal);
		layoutCategorias = (LinearLayout) findViewById(R.id.layout_categorias);
		layoutSubcategorias = (LinearLayout) findViewById(R.id.layout_subcategorias);
		layoutMiniaturas = (LinearLayout) findViewById(R.id.layout_miniaturas);
		layoutSobre = (RelativeLayout) findViewById(R.id.layout_sobre);
		layoutViewPager = (RelativeLayout) findViewById(R.id.layout_viewPager);

		final GridView menuPrincipal = (GridView) findViewById(R.id.gvPrincipal);
		final GridView categorias = (GridView) findViewById(R.id.gvCategorias);
		final GridView subcategorias = (GridView) findViewById(R.id.gvSubcategorias);
		listView = (GridView) findViewById(R.id.gvMiniaturas);
		final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

		Integer[] imagens = { R.drawable.categorias, R.drawable.facebookcompartilhar, R.drawable.sobre, R.drawable.app };
		menuPrincipal.setAdapter(new AdaptadorMenu(Principal.this, imagens));

		menuPrincipal.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
				switch (posicao) {
				case 0:
					Integer[] imagens2 = { R.drawable.categoria_cores, R.drawable.categoria_passoapasso, R.drawable.categoria_glitter, R.drawable.categoria_francesinha,
							R.drawable.categoria_casamento, R.drawable.categoria_animais, R.drawable.categoria_desenhos, R.drawable.categoria_jogos, R.drawable.categoria_tresd,
							R.drawable.categoria_paises, R.drawable.categoria_times, R.drawable.categoria_balada, R.drawable.anonovo, R.drawable.carnaval, R.drawable.natal, R.drawable.decoradas,
							R.drawable.filhaunica, R.drawable.flores, R.drawable.coracoes, R.drawable.nomes };
					categorias.setAdapter(new AdaptadorMenu(Principal.this, imagens2));
					layoutPrincipal.setVisibility(View.GONE);
					layoutCategorias.setVisibility(View.VISIBLE);
					layoutSobre.setVisibility(View.GONE);
					layoutSubcategorias.setVisibility(View.GONE);
					telaAlvo = "categorias";
					break;
				case 1:
					final Intent intent1 = new Intent(Intent.ACTION_SEND);
					intent1.setType("text/plain");
					intent1.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=br.adevt.brasilunhas");

					try {
						startActivity(Intent.createChooser(intent1, "Selecione onde compartilhar:"));
					} catch (android.content.ActivityNotFoundException ex) {
					}

					break;
				case 2:
					layoutPrincipal.setVisibility(View.GONE);
					layoutCategorias.setVisibility(View.GONE);
					layoutSobre.setVisibility(View.VISIBLE);
					layoutSubcategorias.setVisibility(View.GONE);
					telaAlvo = "categorias";
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
				Integer[] imagens = { R.drawable.sub_categoria_amarela, R.drawable.sub_categoria_azul, R.drawable.sub_categoria_branca, R.drawable.sub_categoria_cinza,
						R.drawable.sub_categoria_laranja, R.drawable.sub_categoria_marrom, R.drawable.sub_categoria_preta, R.drawable.sub_categoria_rosa, R.drawable.sub_categoria_roxa,
						R.drawable.sub_categoria_verde, R.drawable.sub_categoria_vermelha };
				String categoriaAux = "miniaturas";
				imageUrls.clear();
				switch (posicao) {
				case 0:
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
					categoriaSelecionada = "anonovo";
					break;
				case 13:
					categoriaSelecionada = "carnaval";
					break;
				case 14:
					categoriaSelecionada = "natal";
					break;
				case 15:
					categoriaSelecionada = "decoradas";
					break;
				case 16:
					categoriaSelecionada = "filhaunica";
					break;
				case 17:
					categoriaSelecionada = "flores";
					break;
				case 18:
					categoriaSelecionada = "coracao";
					break;
				case 19:
					categoriaSelecionada = "nome";
					break;
				}
				// Toast.makeText(Principal.this, categoriaSelecionada,
				// Toast.LENGTH_SHORT).show();
				// carregarImagens(categoriaSelecionada, miniaturas);
				layoutPrincipal.setVisibility(View.GONE);
				layoutCategorias.setVisibility(View.GONE);
				layoutSobre.setVisibility(View.GONE);

				if (categoriaAux.equals("subcategoria1")) {
					subcategorias.setAdapter(new AdaptadorMenu(Principal.this, imagens));
					layoutCategorias.setVisibility(View.GONE);
					layoutSubcategorias.setVisibility(View.VISIBLE);
				} else {
					new CarregaURLs().execute();
					// ((GridView) listView).setAdapter(new ImageAdapter());
					// layoutSubcategorias.setVisibility(View.GONE);
					// layoutMiniaturas.setVisibility(View.VISIBLE);
				}
				telaAlvo = "miniaturas";
			}
		});
		subcategorias.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				imageUrls.clear();
				switch (posicao) {
				case 0:
					categoriaSelecionada = "amarela";
					break;
				case 1:
					categoriaSelecionada = "azul";
					break;
				case 2:
					categoriaSelecionada = "branca";
					break;
				case 3:
					categoriaSelecionada = "cinza";
					break;
				case 4:
					categoriaSelecionada = "laranja";
					break;
				case 5:
					categoriaSelecionada = "marrom";
					break;
				case 6:
					categoriaSelecionada = "preta";
					break;
				case 7:
					categoriaSelecionada = "rosa";
					break;
				case 8:
					categoriaSelecionada = "roxa";
					break;
				case 9:
					categoriaSelecionada = "verde";
					break;
				case 10:
					categoriaSelecionada = "vermelha";
					break;
				}

				telaAlvo = "subcategoria1";
				new CarregaURLs().execute();
			}

		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				String[] urls = new String[imageUrls.size()];
				for (int n = 0; n < imageUrls.size(); n++) {
					urls[n] = "" + imageUrls.get(n);
				}
				viewPager.setAdapter(new ImagePagerAdapter(urls));
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
				layoutSobre.setVisibility(View.GONE);
				layoutViewPager.setVisibility(View.VISIBLE);
				telaAlvo = "viewpager";
			}
		});

		btnCompartilhar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, imageUrls.get(posicaoPW));

				try {
					startActivity(Intent.createChooser(intent, "Selecione onde compartilhar:"));
				} catch (android.content.ActivityNotFoundException ex) {
				}
			}
		});

		btnSalvar.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				final File imageFile = imageLoader.getDiscCache().get(imageUrls.get(viewPager.getCurrentItem()));
				final ProgressDialog ringProgressDialog = ProgressDialog.show(Principal.this, "Aguarde...", "Baixando imagem...", true);
				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							saveImageToExternalStorage(BitmapFactory.decodeFile(imageFile.getPath()), imageFile.getName());
						} catch (Exception e) {
						}
						ringProgressDialog.dismiss();
					}
				}).start();
				Toast.makeText(Principal.this, "Salva em: " + fullPath + "/", Toast.LENGTH_LONG).show();
			}
		});

		btnAvaliarApp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("market://details?id=br.adevt.brasilunhas"));
				startActivity(i);
			}
		});

	}

	// private void carregarImagens(String categoria, GridView miniaturas) {
	// String[] urls = {
	// "http://2.bp.blogspot.com/-ZBdmSCOFkus/ULrJPo69BII/AAAAAAAAEiE/kMmzSjEkmfw/s1600/unhas-acucar-1.jpg",
	// };
	// new CarregaImagem(urls, miniaturas).execute();
	// }
	//
	class CarregaURLs extends AsyncTask<String, String, String> {

		public CarregaURLs() {

		}

		public void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			try {

				URL aURL = new URL("http://www.adevt.com.br/brasilunhas/service/imagens.php?categoria=" + categoriaSelecionada);
				URLConnection conn = (URLConnection) aURL.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String linha;
				while ((linha = in.readLine()) != null) {
					imageUrls.add(linha.trim());
				}

				in.close();

				// return d;
				Toast.makeText(Principal.this, "foi", Toast.LENGTH_SHORT).show();
				Log.d("url", imageUrls.size() == 0 ? "vazio" : imageUrls.get(0));
				return null;
			} catch (Exception e) {
				System.out.println("Exc=" + e);
				return null;
			}

		}

		public void onPostExecute(String a) {
			((GridView) listView).setAdapter(new ImageAdapter());
			layoutSubcategorias.setVisibility(View.GONE);
			layoutMiniaturas.setVisibility(View.VISIBLE);
		}

	}

	public boolean saveImageToExternalStorage(Bitmap image, String nome) {
		final String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

		try {
			File dir = new File(fullPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			OutputStream fOut = null;
			File file = new File(fullPath, nome + ".png");
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
			layoutSobre.setVisibility(View.GONE);
			telaAlvo = "principal";
			break;
		case "subcategoria":
			layoutPrincipal.setVisibility(View.GONE);
			layoutSubcategorias.setVisibility(View.GONE);
			layoutCategorias.setVisibility(View.VISIBLE);
			layoutMiniaturas.setVisibility(View.GONE);
			layoutViewPager.setVisibility(View.GONE);
			layoutSobre.setVisibility(View.GONE);
			telaAlvo = "categorias";
			break;
		case "subcategoria1":
			layoutPrincipal.setVisibility(View.GONE);
			layoutSubcategorias.setVisibility(View.VISIBLE);
			layoutCategorias.setVisibility(View.GONE);
			layoutMiniaturas.setVisibility(View.GONE);
			layoutSobre.setVisibility(View.GONE);
			layoutViewPager.setVisibility(View.GONE);
			telaAlvo = "subcategoria";
			break;
		case "miniaturas":
			layoutPrincipal.setVisibility(View.GONE);
			layoutSubcategorias.setVisibility(View.GONE);
			layoutCategorias.setVisibility(View.VISIBLE);
			layoutMiniaturas.setVisibility(View.GONE);
			layoutSobre.setVisibility(View.GONE);
			layoutViewPager.setVisibility(View.GONE);
			telaAlvo = "categorias";
			break;
		case "viewpager":
			layoutPrincipal.setVisibility(View.GONE);
			layoutSubcategorias.setVisibility(View.GONE);
			layoutCategorias.setVisibility(View.GONE);
			layoutMiniaturas.setVisibility(View.VISIBLE);
			layoutSobre.setVisibility(View.GONE);
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

	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
				assert view != null;
				holder = new ViewHolder();
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			imageLoader.displayImage(imageUrls.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					holder.progressBar.setProgress(0);
					holder.progressBar.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					holder.progressBar.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					holder.progressBar.setVisibility(View.GONE);
				}
			}, new ImageLoadingProgressListener() {
				@Override
				public void onProgressUpdate(String imageUri, View view, int current, int total) {
					holder.progressBar.setProgress(Math.round(100.0f * current / total));
				}
			});

			return view;
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
					}
					Toast.makeText(Principal.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}

	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}

	// static class Imagem implements Serializable {
	// //@Key
	//
	//
	// }

}
