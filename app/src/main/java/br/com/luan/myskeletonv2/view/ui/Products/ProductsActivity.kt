package br.com.luan.myskeletonv2.view.ui.Products

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.model.Product
import br.com.luan.myskeletonv2.data.model.Resource
import br.com.luan.myskeletonv2.data.request.ProductRequestActivity
import br.com.luan.myskeletonv2.extras.EndlessRecyclerViewScrollListener
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.adapter.RecycleProdutoListaAdapter
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : BaseDrawerActivity(), ProductRequestActivity.RequestListener {

    lateinit var adapter:RecycleProdutoListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        setSupportActionBar(toolbar)

        ProductRequestActivity(this@ProductsActivity, this@ProductsActivity).getProducts(0, false)

    }


    fun getProducts(products: ArrayList<Product>) {
        adapter = RecycleProdutoListaAdapter(this)
        if(GridProdutosList != null) {
            GridProdutosList.setHasFixedSize(true)
            val llm = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            llm.setSpanSizeLookup(
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            when (adapter.getItemViewType(position)) {
                                2 -> return 2
                                1 -> return 1
                                0 -> return 2
                                else -> return -1
                            }
                        }
                    })
            GridProdutosList.setAdapter(adapter)
            GridProdutosList.isFocusable = false
            GridProdutosList.layoutManager = llm
            adapter.replace(products)


            GridProdutosList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(llm) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                    adapter.isLoading = true
                    adapter.notifyItemChanged(adapter.getItemCount())
                    Handler().postDelayed(object : Runnable {
                        override fun run() {
                            try {
                                adapter.isLoading = false
                                adapter.notifyItemChanged(adapter.getItemCount());
                                ProductRequestActivity(this@ProductsActivity, this@ProductsActivity).getProducts(page + 1, true)

//                                ProductRequestActivity(activity, this@TodosFragment).getTodos(page + 1, true)
                            } catch (e: Exception) {

                            }
                        }
                    }, 3000);
                }
            })


            adapter.addRecyclerViewOnClickListenerHack(object : RecyclerViewOnClickListenerHack {
                override fun onClickListener(view: View, `object`: Any, position: Int) {
                    val produto = `object` as Product
//                    startActivity(Intent(baseContext, DetailsActivity::class.java)
//                            .putExtra("object_id", produto.id))
                }

                override fun onClickListener(view: View, position: Int) {

                }
            })
        }
    }


    override fun onSuccess(resource: Resource,isPaginator:Boolean) {
        if(resource.products != null) {
            if(isPaginator)
                adapter.add(resource!!.products!!.toCollection(ArrayList()))
            else
                getProducts(resource!!.products!!.toCollection(ArrayList()))
        }
    }

    override fun onError(error: String) {
        onErrorAlert(error)
    }


}
