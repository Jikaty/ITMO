import polars as pl

# Читаем Excel файл
excelDb = pl.read_excel("C:/Users/Timoxa/Documents/ITMO/Инфа/Лаба 5 инфа.xlsx")


showDb = excelDb[1:13, :21]


pl.Config.set_tbl_rows(20)
pl.Config.set_tbl_cols(22)
pl.Config.set_tbl_width_chars(1000)

print(showDb)




