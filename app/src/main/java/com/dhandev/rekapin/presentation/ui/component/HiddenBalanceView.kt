
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.ui.theme.RekapinTheme

@Composable
fun HiddenBalanceView(
    modifier: Modifier = Modifier
){
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Black, Color.White),
        start = Offset(200f, 0f),
        end = Offset(0f, 200f)
    )
    val gradient2 = Brush.linearGradient(
        colors = listOf(Color.White, Color.Black),
        start = Offset(200f, 0f),
        end = Offset(0f, 200f)
    )
    Row(
        modifier = modifier.fillMaxWidth().padding(top = 10.dp, end = 10.dp)
    ){
        repeat(7){
            Surface(
                modifier = Modifier.size(32.dp).padding(4.dp).clip(CircleShape).background(brush = if (it % 2 == 0) gradient else gradient2),
                shape = CircleShape,
                color = Color.Transparent
            ){}
        }
    }
}

@Preview
@Composable
fun PreviewHiddenBalance(){
    RekapinTheme {
        Surface(
            color = Color.White
        ) {
            HiddenBalanceView()
        }
    }
}