package kndroidx.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.github.kndroidx.BR

abstract class BaseDialog<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    DialogFragment() {
    private lateinit var _binding: VB
    val binding: VB get() = _binding

    // abstract val viewModel: ViewModel
    open val animation: Animation = AlphaAnimation(0.0f, 1.0f)
    open val isAnimation = true
    open fun init() {}

    fun show(manager: FragmentManager) {
        show(manager, this::class.java.name)
    }

    override fun getTheme() = super.theme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.apply {
                setBackgroundDrawable(Color.BLACK.toDrawable())
                decorView.setPadding(0, 0, 0, 0)
                attributes = attributes.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                WindowCompat.setDecorFitsSystemWindows(this, false)
                with(WindowCompat.getInsetsController(this, decorView)) {
                    systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    hide(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = inflate(inflater)
        (binding as? ViewDataBinding)?.let {
            it.lifecycleOwner = this
            it.setVariable(BR.dialog, this)
        }
        init()
        if (isAnimation)
            binding.root.startAnimation(animation)
        return binding.root
    }

    override fun dismiss() {
        if (isAnimation) {
            binding.root.startAnimation(animation)
            Handler(Looper.getMainLooper()).postDelayed({ super.dismiss() }, animation.duration)
        } else
            super.dismiss()
    }

}