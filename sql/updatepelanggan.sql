/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  USER
 * Created: Aug 2, 2019
 */
update PELANGGAN set nik=true where NPWP like '%Nik%'
update PELANGGAN set npwp='00.000.000.0-000.000' where NPWP ='-'
update PELANGGAN set npwp='00.000.000.0-000.000' where NPWP ='0'
