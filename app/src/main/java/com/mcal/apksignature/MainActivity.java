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

		private TextView resultTextSHA;
		private TextView resultTextSHA1;
		private TextView resultTextSHA256;
		private TextView resultTextSHA384;
		private TextView resultTextSHA512;
		private TextView resultTextMD5;
		@Override
		protected void onCreate ( Bundle savedInstanceState )
			{
				super.onCreate ( savedInstanceState );
				setContentView ( R.layout.activity_main );

				packageNameField = (EditText) findViewById ( R.id.packageField );

				resultTextSHA = (TextView) findViewById ( R.id.resultSHA );
				resultTextSHA1 = (TextView) findViewById ( R.id.resultSHA1 );
				resultTextSHA256 = (TextView) findViewById ( R.id.resultSHA256 );
				resultTextSHA384 = (TextView) findViewById ( R.id.resultSHA384 );
				resultTextSHA512 = (TextView) findViewById ( R.id.resultSHA512 );
				resultTextMD5 = (TextView) findViewById ( R.id.resultMD5 );
				
				findViewById ( R.id.selectBtn ).setOnClickListener ( this );
				findViewById ( R.id.btnGo ).setOnClickListener ( this );
				findViewById ( R.id.copySHA ).setOnClickListener ( this );
				findViewById ( R.id.copySHA1 ).setOnClickListener ( this );
				findViewById ( R.id.copySHA384 ).setOnClickListener ( this );
				findViewById ( R.id.copySHA512 ).setOnClickListener ( this );
				findViewById ( R.id.copyMD5 ).setOnClickListener ( this );
			}

		@Override
		public void onClick ( View v )
			{
				switch ( v.getId ( ) )
					{
						case R.id.selectBtn:
							onSelect ( );
							break;
						case R.id.btnGo:
							String signSHA = getAPKSignatureSHA ();
							Log.d ( "Signature SHA:", signSHA );
							resultTextSHA.setText ( signSHA );
							
							String signSHA1 = getAPKSignatureSHA1 ();
							Log.d ( "Signature SHA1:", signSHA1 );
							resultTextSHA1.setText ( signSHA1 );
							
							String signSHA256 = getAPKSignatureSHA256 ();
							Log.d ( "Signature SHA256:", signSHA256 );
							resultTextSHA256.setText ( signSHA256 );
							
							String signSHA384 = getAPKSignatureSHA384 ();
							Log.d ( "Signature SHA384:", signSHA384 );
							resultTextSHA384.setText ( signSHA384 );
							
							String signSHA512 = getAPKSignatureSHA512 ();
							Log.d ( "Signature SHA512:", signSHA512 );
							resultTextSHA512.setText ( signSHA512 );
							
							String signMD5 = getAPKSignatureMD5 ();
							Log.d ( "Signature MD5:", signMD5 );
							resultTextMD5.setText ( signMD5 );
							break;
						case R.id.copySHA:
							copySHA ( );
							break;
						case R.id.copySHA1:
							copySHA1 ( );
							break;
						case R.id.copySHA256:
							copySHA256 ( );
							break;
						case R.id.copySHA384:
							copySHA384 ( );
							break;
						case R.id.copySHA512:
							copySHA512 ( );
							break;
						case R.id.copyMD5:
							copyMD5 ( );
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
								MessageDigest crc = null;
								try
									{
										crc = MessageDigest.getInstance ( "CRC32" );
										crc.update( signature.toByteArray ( ) );
										return Base64.encodeToString ( crc.digest ( ), Base64.DEFAULT );
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
		String getAPKSignatureMD5 ( )
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

		void copySHA ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextSHA.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}
			
		void copySHA1 ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextSHA1.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}
			
		void copySHA256 ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextSHA256.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}
			
		void copySHA384 ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextSHA384.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}
			
		void copySHA512 ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextSHA512.getText ( ).toString ( ) );
				Toast.makeText ( this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
			}
			
		void copyMD5 ( )
			{
				ClipboardManager cmb = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
				cmb.setText ( resultTextMD5.getText ( ).toString ( ) );
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
