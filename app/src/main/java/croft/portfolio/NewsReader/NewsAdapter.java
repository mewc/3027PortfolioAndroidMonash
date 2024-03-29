package croft.portfolio.NewsReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import croft.portfolio.NewsReader.models.Article;
import croft.portfolio.R;


/**
 * Created by Michaels on 17/4/2016.
 */
public class NewsAdapter extends BaseAdapter {

    private ArrayList<Article> articles;
    private Context context;

    public NewsAdapter (Context context, ArrayList<Article> articles){
        this.context = context;
        this.articles = articles;
    }

    //THis class is connected to the article list item - it fills that view and returns it to be put into the list
    public static class ViewHolder {

        ImageView icon;
        TextView headline;
        TextView creator;
        TextView date;
        //dont need to show link here, that is stored and retrieved when the column is clicked
        TextView category;

    }

    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.news_item_list, null);

            holder = new ViewHolder();

            holder.icon = (ImageView) view.findViewById(R.id.iconArticle);
            holder.headline = (TextView) view.findViewById(R.id.headlineText);
            holder.creator = (TextView) view.findViewById(R.id.creatorText);
            holder.date = (TextView) view.findViewById(R.id.dateText);
            holder.category = (TextView) view.findViewById(R.id.categoryText);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        String iconLink = articles.get(i).getIconLink();
        String headline = articles.get(i).getHeadline();
        String creator = articles.get(i).getCreator();
        String date   = articles.get(i).getPublishDate().toString();
        String category = articles.get(i).getCategory();

        new DownloadImageTask(holder.icon).execute(iconLink);



        holder.headline.setText(headline);
        holder.creator.setText(creator);
        holder.date.setText(date);
        holder.category.setText(category);

        return view;

    }



    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return articles.get(position).getId();
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView iv;


        public DownloadImageTask(ImageView iv) {
            this.iv = iv;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap image = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("ERROR getting image", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {
            iv.setImageBitmap(result);
        }
    }

}
