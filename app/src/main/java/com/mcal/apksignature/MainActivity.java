package com.mcal.apksignature;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Log;
import android.util.Base64;
import java.util.zip.*;

public class MainActivity extends Activity implements View.OnClickListener
	{

		private EditText packageNameField;

		private TextView resultTextView;

		@Override
		protected void onCreate ( Bundle savedInstanceState )
			{
				super.onCreate ( savedInstanceState );
				setContentView ( R.layout.activity_main );

				packageNameField = (EditText) findViewById ( R.id.packageField );

				resultTextView = (TextView) findViewById ( R.id.resultText );

				findViewById ( R.id.selectBtn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureCRC32Btn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureSHABtn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureSHA1Btn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureSHA256Btn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureSHA384Btn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureSHA512Btn ).setOnClickListener ( this );
				findViewById ( R.id.genSignatureMDBtn ).setOnClickListener ( this );
				findViewById ( R.id.copyBtn ).setOnClickListener ( this );
			}

		@Override
		public void onClick ( View v )
			{
				switch ( v.getId ( ) )
					{
						case R.id.selectBtn:
							onSelect ( );
							break;
						case R.id.genSignatureCRC32Btn:
							String signCRC32 = getAPKSignatureCRC32 ();
							Log.d ( "Signature:", signCRC32 );
							resultTextView.setText ( signCRC32 );
							break;
						case R.id.genSignatureSHABtn:
							String signSHA = getAPKSignatureSHA ();
							Log.d ( "Signature:", signSHA );
							resultTextView.setText ( signSHA );
							break;
						case R.id.genSignatureSHA1Btn:
							String signSHA1 = getAPKSignatureSHA1 ();
							Log.d ( "Signature:", signSHA1 );
							resultTextView.setText ( signSHA1 );
							break;
						case R.id.genSignatureSHA256Btn:
							String signSHA256 = getAPKSignatureSHA256 ();
							Log.d ( "Signature:", signSHA256 );
							resultTextView.setText ( signSHA256 );
							break;
						case R.id.genSignatureSHA384Btn:
							String signSHA384 = getAPKSignatureSHA384 ();
							Log.d ( "Signature:", signSHA384 );
							resultTextView.setText ( signSHA384 );
							break;
						case R.id.genSignatureSHA512Btn:
							String signSHA512 = getAPKSignatureSHA512 ();
							Log.d ( "Signature:", signSHA512 );
							resultTextView.setText ( signSHA512 );
							break;
						case R.id.genSignatureMDBtn:
							String signMD = getAPKSignatureMD ( );
							Log.d ( "Signature:", signMD );
							resultTextView.setText ( signMD );
							break;
						case R.id.copyBtn:
							copy ( );
							break;
						default:
							break;
					}
			}

		void onSelect ( )
			{
				Intent intent = new Intent ( MainActivity.this, PackageListActivity.class );
				startActivityForResult ( intent, 1 );
			}
			
		// Получение CRC32 подписи
		String getAPKSignatureCRC32 ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md32 = null;
								try
									{
										md32 = MessageDigest.getInstance ( "CRC32" );
										byte[] digest = md32.digest( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
		
			
		// Получение SHA подписи
		String getAPKSignatureSHA ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest sha = null;
								try
									{
										sha = MessageDigest.getInstance ( "SHA" );
										sha.update ( signature.toByteArray ( ) );
										return Base64.encodeToString ( sha.digest ( ), Base64.DEFAULT );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
			
		// Получение SHA1 подписи
		String getAPKSignatureSHA1 ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md = null;
								try
									{
										md = MessageDigest.getInstance ( "SHA1" );
										byte[] digest = md.digest( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
			
		// Получение SHA256 подписи
		String getAPKSignatureSHA256 ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md256 = null;
								try
									{
										md256 = MessageDigest.getInstance ( "SHA256" );
										byte[] digest = md256.digest( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
			
		// Получение SHA384 подписи
		String getAPKSignatureSHA384 ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md384 = null;
								try
									{
										md384 = MessageDigest.getInstance ( "SHA384" );
										byte[] digest = md384.digest( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
			
		// Получение SHA512 подписи
		String getAPKSignatureSHA512 ()
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md512 = null;
								try
									{
										md512 = MessageDigest.getInstance ( "SHA512" );
										byte[] digest = md512.digest( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}
			
		// Получение MD5 подписи
		String getAPKSignatureMD ( )
			{
				String name = packageNameField.getText ( ).toString ( ).trim ( );
				if ( name == null || name.isEmpty ( ) )
					{
						Toast.makeText ( this, "Выберите приложение", Toast.LENGTH_LONG ).show ( );
						return "";
					}

				try
					{
						PackageInfo info = getPackageManager ( ).getPackageInfo ( name, PackageManager.GET_SIGNATURES );
						if ( info.signatures != null && info.signatures.length > 0 )
							{
								Signature signature = info.signatures [ 0 ];
								MessageDigest md5 = null;
								try
									{
										md5 = MessageDigest.getInstance ( "MD5" );
										byte[] digest = md5.digest ( signature.toByteArray ( ) );
										return toHexString ( digest );
									}
								catch (NoSuchAlgorithmException e)
									{
										e.printStackTrace ( );
									}
							}
					}
				catch (PackageManager.NameNotFoundException e)
					{
						Toast.makeText ( this, "Приложение не существует", Toast.LENGTH_LONG ).show ( );
					}
				return "";
			}

		void copy ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextView.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}

		@Override
		protected void onActivityResult ( int requestCode, int resultCode, Intent data )
			{
				try
					{
						String packageName = data.getStringExtra ( "packageName" );
						packageNameField.setText ( packageName );
					}
				catch (Exception e)
					{

					}
			}

		private static final char[] HEX_CHAR = {
				'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
			};

		private String toHexString ( byte[] rawByteArray )
			{
				char[] chars = new char[rawByteArray.length * 2];
				for ( int i = 0; i < rawByteArray.length; ++i )
					{
						byte b = rawByteArray [ i ];
						chars [ i * 2 ] = HEX_CHAR [ ( b >>> 4 & 0x0F ) ];
						chars [ i * 2 + 1 ] = HEX_CHAR [ ( b & 0x0F ) ];
					}
				return new String ( chars );
			}
	}
