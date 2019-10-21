/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  USER
 * Created: Sep 13, 2019
 */
create view viewstokdo as 
(select kodebarang,tanggal, kodebatch, dr.jumlahkecil as in, 0 as out, 'DO' as jenis, 1 as nourut
from do d 
inner join DORINCI dr on d.id = dr.iddo 
union all
select kodebarang,tanggal, kodebatch, 0 as in, rp.jumlahkecil as out, 'JUAL' as jenis, 2 as nourut
from PENJUALAN p 
inner join RINCIPENJUALAN rp on p.id = rp.IDPENJUALAN where rp.IDDO>0
union all
select kodebarang,tanggal, kodebatch, 0 as in, rdr.jumlahkecil as out, 'RETURDO' as jenis, 3 as nourut
from RETURDO r 
inner join RETURDORINCI rdr on r.id = rdr.IDRETURDO)
order by 2, 7;