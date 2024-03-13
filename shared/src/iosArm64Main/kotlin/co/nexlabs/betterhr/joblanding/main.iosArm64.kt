package co.nexlabs.betterhr.joblanding

import App
import moe.tlaster.precompose.PreComposeApplication
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface


fun MainViewController() = PreComposeApplication { App() }

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}