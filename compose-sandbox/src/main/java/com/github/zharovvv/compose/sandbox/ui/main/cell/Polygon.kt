package com.github.zharovvv.compose.sandbox.ui.main.cell

import android.graphics.PointF
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

@Composable
fun Polygon(
    vertexCount: Int
) {
    val density = LocalDensity.current
    val shape = remember(vertexCount) {
//        val radius = with(density) { PolygonSize.width.toPx() / 2f }
//        RoundedPolygonShape(
//            polygon = RoundedPolygon(
//                numVertices = vertexCount,
////                radius = radius,
////                centerX = with(density) { PolygonSize.width.toPx() / 2f },
////                centerY = with(density) { PolygonSize.height.toPx() / 2f },
////                rounding = CornerRounding(radius = 0.2f * radius)
//                rounding = CornerRounding(radius = 0.2f)
//            )
//        )
        GptPolygonShape(
            sides = vertexCount,
            cornerRadius = 15f
        )
    }
    Box(
        modifier = Modifier
            .size(PolygonSize)
            .clip(shape)
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .clickable { }
    )

}

fun RoundedPolygon.getBounds() = calculateBounds().let { Rect(it[0], it[1], it[2], it[3]) }
class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private var matrix: Matrix = Matrix()
) : Shape {
    private var path = Path()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()
        val bounds = polygon.getBounds()
        val maxDimension = max(bounds.width, bounds.height)
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top)
//        matrix.rotateZ(-90f)

        path.transform(matrix)
        return Outline.Generic(path)
    }
}

class GptPolygonShape(
    private val sides: Int,
    private val cornerRadius: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        require(sides >= 3) { "Polygon must have at least 3 sides" }
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = min(centerX, centerY) - cornerRadius

        // Углы многоугольника
        val angleStep = 2 * Math.PI / sides
        val vertices = (0 until sides).map { i ->
            val angle = i * angleStep - Math.PI / 2
            PointF(
                centerX + radius * cos(angle).toFloat(),
                centerY + radius * sin(angle).toFloat()
            )
        }

        // Построение скругленных углов
        vertices.forEachIndexed { i, current ->
            val prev = vertices[(i - 1 + sides) % sides]
            val next = vertices[(i + 1) % sides]

            // Вектор от текущей точки к соседним
            val toPrev = offsetPoint(current, prev, cornerRadius)
            val toNext = offsetPoint(current, next, cornerRadius)

            if (i == 0) {
                path.moveTo(toPrev.x, toPrev.y)
            }

            // Добавляем кривую
            path.lineTo(toPrev.x, toPrev.y)
            path.quadraticTo(current.x, current.y, toNext.x, toNext.y)
        }

        path.close()
        return Outline.Generic(path)
    }

    private fun offsetPoint(from: PointF, to: PointF, distance: Float): PointF {
        val vectorX = to.x - from.x
        val vectorY = to.y - from.y
        val length = hypot(vectorX.toDouble(), vectorY.toDouble()).toFloat()
        val scale = distance / length
        return PointF(
            from.x + vectorX * scale,
            from.y + vectorY * scale
        )
    }
}


private val PolygonSize = DpSize(width = 150.dp, height = 150.dp)