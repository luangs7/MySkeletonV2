package br.com.luan.myskeletonv2.view.ui.ShimmerList


import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.request.ShimmerRequestActivity
import br.com.luan.myskeletonv2.utils.onErrorAlert
import br.com.luan.myskeletonv2.view.ui.BaseActivity
import br.com.luan.myskeletonv2.view.ui.ShimmerList.adapter.RecycleProdutoPedidoAdapter
import br.com.luan.myskeletonv2.view.ui.ShimmerList.adapter.RecycleShimmerAdapter
import br.com.luan.myskeletonv2.view.ui.ShimmerList.model.Order
import kotlinx.android.synthetic.main.activity_pedido_list.*

class ShimmerListActivity : BaseActivity(),ShimmerRequestActivity.RequestListener {
    
    lateinit var adapter: RecycleProdutoPedidoAdapter
    var myProducts:ArrayList<Order> = ArrayList()
    lateinit var shimmerAdapter: RecycleShimmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_list)
        shimmerAdapter = RecycleShimmerAdapter(this,R.layout.adapter_pedidos_shimmer)

        pedidosList.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        pedidosList.setLayoutManager(llm)
        pedidosList.adapter = shimmerAdapter


        setSupportActionBar(this.toolbar)

        ShimmerRequestActivity(this,this).getOrders()

    }
    
    
    fun initList(myProducts:ArrayList<Order>){

        adapter = RecycleProdutoPedidoAdapter(this)
        pedidosList.setAdapter(adapter)
        adapter.replace(myProducts)


//        <------------- To add a clicklistener on list item -------------->

//        adapter.addRecyclerViewOnClickListenerHack(object : RecyclerViewOnClickListenerHack {
//            override fun onClickListener(view: View, `object`: Any, position: Int) {
//                val produto = `object` as Order
//                startActivity(Intent(baseContext, PedidoDetailsActivity::class.java)
//                        .putExtra("object_id", produto.id
//                        ))
//            }
//
//            override fun onClickListener(view: View, position: Int) {
//
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as android.support.v7.widget.SearchView

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))
        val searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(Color.WHITE) // set the text color
        searchEditText.setHintTextColor(Color.WHITE)
        searchEditText.hint = "Buscar"

        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes.set(searchEditText, R.drawable.cursor) //This sets the cursor resource ID to 0 or @null which will make it visible on white background


            val closeField = android.support.v7.widget.SearchView::class.java.getDeclaredField("mCloseButton")
            closeField.isAccessible = true
            val closeBtn = closeField.get(searchView) as ImageView
            closeBtn.setImageResource(R.drawable.ic_close)

            val searchField = android.support.v7.widget.SearchView::class.java.getDeclaredField("mSearchButton")
            searchField.isAccessible = true
            val searchbtn = searchField.get(searchView) as ImageView
            searchbtn.setImageResource(R.drawable.ic_search)


        } catch (e: Exception) {
        }

        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.getFilter().filter(query)
                hideKeybord()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.getFilter().filter(query)

                hideKeybord();
                return true
            }
        })

        searchView.setOnCloseListener {
            adapter.getFilter().filter("")
            adapter.refreshSearch()
            false
        }
        return true

    }

    override fun onSuccess(orders: List<Order>?) {
        if (orders != null) {
            initList(orders.toCollection(ArrayList()))
            empty.visibility = View.GONE
        }else{
            empty.visibility = View.VISIBLE
            pedidosList.visibility = View.GONE

        }
    }

    override fun onError(error: String) {
        onErrorAlert(error)
    }


    fun hideKeybord() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
