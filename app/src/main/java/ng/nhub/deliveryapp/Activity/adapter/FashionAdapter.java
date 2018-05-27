package ng.nhub.deliveryapp.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ng.nhub.deliveryapp.Activity.model.FashionModel;
import ng.nhub.deliveryapp.R;

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.ViewHolder>{

    private Context context;
    private List<FashionModel> fashionModels;

    public FashionAdapter(Context _context, List<FashionModel> models) {
        this.context = _context;
        this.fashionModels = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fashion_item,
                parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FashionModel fashionModel = fashionModels.get(position);
        holder.name.setText(fashionModel.getName());
        holder.price.setText(fashionModel.getPrice());

        Glide.with(context)
                .load(fashionModel.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.fashion_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener(){
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_to_favorite:
                    Toast.makeText(context, "Add to favorite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_to_cart:
                    Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;

        }
    }

    @Override
    public int getItemCount() {
        return fashionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, price;
        private ImageView thumbnail, overflow;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            overflow = itemView.findViewById(R.id.overflow);

            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(context, "You clicked " + position, Toast.LENGTH_SHORT).show();
        }
    }
}
