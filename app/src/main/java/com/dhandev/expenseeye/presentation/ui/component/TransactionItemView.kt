package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.TransactionItemModel
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.MyGreen
import com.dhandev.expenseeye.ui.theme.MyRed
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.StringUtil
import com.dhandev.expenseeye.utils.TransactionCategory

@Composable
fun TransactionItemView(
    modifier: Modifier = Modifier,
    data: TransactionItemModel
) {
    val categoryMap = mapOf(
        TransactionCategory.Income.toString() to Pair(
            R.drawable.ic_payment,
            R.string.category_payment
        ),
        TransactionCategory.Gift.toString() to Pair(R.drawable.ic_gift, R.string.category_gift),
        TransactionCategory.Food.toString() to Pair(
            R.drawable.ic_food_drinks,
            R.string.category_food
        ),
        TransactionCategory.Transportation.toString() to Pair(
            R.drawable.ic_transportation,
            R.string.category_transportation
        ),
        TransactionCategory.Donation.toString() to Pair(
            R.drawable.ic_donate,
            R.string.category_donation
        ),
        TransactionCategory.Bills.toString() to Pair(R.drawable.ic_bill, R.string.category_bill),
        TransactionCategory.Health.toString() to Pair(
            R.drawable.ic_health,
            R.string.category_health
        ),
        TransactionCategory.Exercise.toString() to Pair(
            R.drawable.ic_exercise,
            R.string.category_exercise
        ),
        TransactionCategory.Education.toString() to Pair(
            R.drawable.ic_education,
            R.string.category_education
        )
    )

    val (categoryImage, categoryName) = categoryMap[data.category] ?: Pair(
        R.drawable.ic_expenseeye,
        R.string.app_name
    )

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(categoryImage),
            contentDescription = stringResource(id = R.string.category_image_desc)
        )
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        ) {
            Text(text = data.title, style = raleway(fontSize = 12, weight = FontWeight.Medium))
            Text(
                text = stringResource(id = categoryName),
                style = raleway(fontSize = 10, weight = FontWeight.Normal)
            )
        }
        Text(
            text = StringUtil.formatRpWithSign(data.total.toString(), data.isExpense),
            style = raleway(fontSize = 14, weight = FontWeight.SemiBold),
            color = if (data.isExpense) MyRed else MyGreen
        )
    }
}

@Preview
@Composable
fun PreviewItem() {
    ExpenseEyeTheme {
        Surface {
            TransactionItemView(
                data = TransactionItemModel(
                    0,
                    "Apa ya",
                    10012112,
                    100000.0,
                    TransactionCategory.Health.toString(),
                    false
                )
            )
        }
    }
}