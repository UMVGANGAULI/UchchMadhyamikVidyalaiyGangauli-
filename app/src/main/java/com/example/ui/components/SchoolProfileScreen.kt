package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.MockDataRepository
import com.example.ui.theme.*

@Composable
fun SchoolProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero Campus Image Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_school_banner),
                        contentDescription = "विद्यालय परिसर - उ.मा.वि. गंगौली",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "परिसर दृश्य • उ.मा.वि. गंगौली (सिमरी, बक्सर)",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        // Introduction Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "विद्यालय का ऐतिहासिक परिचय (About UMV Gangauli)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue
                )
                Text(
                    text = "उच्च माध्यमिक विद्यालय, गंगौली की स्थापना वर्ष 1972 में बक्सर जिले के सिमरी प्रखंड अंतर्गत ग्रामीण क्षेत्र के छात्र-छात्राओं को गुणवत्तापूर्ण माध्यमिक एवं उच्च माध्यमिक शिक्षा सुलभ कराने के पुनीत उद्देश्य से हुई थी। वर्तमान में यह विद्यालय बिहार विद्यालय परीक्षा समिति (BSEB, पटना) से संबद्ध है तथा कला, विज्ञान एवं वाणिज्य संकाय में कक्षा 9वीं से 12वीं तक शिक्षण प्रदान करता है।",
                    fontSize = 12.sp,
                    color = BiharTextDark,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // Key Institutional Highlights
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "मुख्य विवरणी (Key Institutional Profile)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue
                )
                Spacer(modifier = Modifier.height(8.dp))

                ProfileInfoRow("UDISE+ कोड", "10301901805")
                ProfileInfoRow("विद्यालय स्थापना", "1972")
                ProfileInfoRow("संबद्धता", "बिहार विद्यालय परीक्षा समिति (BSEB)")
                ProfileInfoRow("प्रखंड / अंचल", "सिमरी (Simri)")
                ProfileInfoRow("जिला", "बक्सर (बिहार)")
                ProfileInfoRow("पिन कोड", "802130")
                ProfileInfoRow("संबद्ध कक्षाएं", "कक्षा 9वीं, 10वीं, 11वीं एवं 12वीं")
                ProfileInfoRow("संकाय (Streams)", "विज्ञान, कला एवं सामान्य")
            }
        }

        // Campus Infrastructure & Facilities
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "शैक्षणिक सुविधाएं एवं संसाधन",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue
                )
                Spacer(modifier = Modifier.height(8.dp))

                MockDataRepository.schoolFacilities.forEach { (name, desc) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = TricolorGreen,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextDark
                            )
                            Text(
                                text = desc,
                                fontSize = 11.sp,
                                color = BiharTextMid,
                                lineHeight = 15.sp
                            )
                        }
                    }
                    Divider(color = BiharBorder.copy(alpha = 0.4f))
                }
            }
        }

        // Contact & Helpdesk
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BiharNavyDark),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "संपर्क एवं सहायता केंद्र (Helpline)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "उच्च माध्यमिक विद्यालय, गंगौली\nसिमरी, जिला: बक्सर (बिहार) - 802130",
                    fontSize = 11.5.sp,
                    color = BiharSky,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "ईमेल: contact@umvgangauli.edu.in | हेल्पलाइन: 1800-345-4444",
                    fontSize = 10.5.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 11.5.sp, color = BiharTextMid)
        Text(text = value, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = BiharTextDark)
    }
}
