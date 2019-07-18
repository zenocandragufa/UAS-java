-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2019 at 03:20 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas_kost`
--

-- --------------------------------------------------------

--
-- Table structure for table `jenis`
--

CREATE TABLE `jenis` (
  `id_jenis` varchar(4) NOT NULL,
  `jenis_kamar` varchar(25) NOT NULL,
  `harga_kamar` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jenis`
--

INSERT INTO `jenis` (`id_jenis`, `jenis_kamar`, `harga_kamar`) VALUES
('1', 'Km. Dalaman', 700000),
('2', 'Km. Luar', 500000),
('3', 'Km. Abstrak', 1000000),
('4', 'Km. Tok', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` varchar(4) NOT NULL,
  `nama_kamar` varchar(25) NOT NULL,
  `jenis_kamar` varchar(25) NOT NULL,
  `harga_kamar` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nama_kamar`, `jenis_kamar`, `harga_kamar`) VALUES
('1', 'zeno', 'Km. Dalam', 700000),
('2', 'akua', 'Km. Tok', 100);

-- --------------------------------------------------------

--
-- Table structure for table `penyewa`
--

CREATE TABLE `penyewa` (
  `id_penyewa` varchar(4) NOT NULL,
  `nama_penyewa` varchar(50) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no_telp` int(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyewa`
--

INSERT INTO `penyewa` (`id_penyewa`, `nama_penyewa`, `alamat`, `no_telp`) VALUES
('11', 'zeno', 'jawa', 890),
('12', 'fuuulindang', 'svabc', 43551),
('13', 'fu', 'svabc', 43551),
('14', 'awaw', 'svabc', 43551);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `no_transaksi` varchar(4) NOT NULL,
  `tgl_bayar` varchar(20) NOT NULL,
  `id_penyewa` varchar(4) NOT NULL,
  `nama_penyewa` varchar(50) NOT NULL,
  `id_kamar` varchar(4) NOT NULL,
  `nama_kamar` varchar(25) NOT NULL,
  `jenis_kamar` varchar(25) NOT NULL,
  `harga_kamar` int(15) NOT NULL,
  `bayar` int(15) NOT NULL,
  `kekurangan` int(15) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`no_transaksi`, `tgl_bayar`, `id_penyewa`, `nama_penyewa`, `id_kamar`, `nama_kamar`, `jenis_kamar`, `harga_kamar`, `bayar`, `kekurangan`, `keterangan`) VALUES
('1', '2019-07-27', '11', 'zeno', '1', 'S', 'Km. Abstrak', 700000, 900000, 0, 'ads'),
('2', '2019-07-01', '14', 'awaw', '2', 'aku', 'Km. Tok', 100, 200, 50, 'Lunas');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jenis`
--
ALTER TABLE `jenis`
  ADD PRIMARY KEY (`id_jenis`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`),
  ADD UNIQUE KEY `jenis_kamar` (`jenis_kamar`);

--
-- Indexes for table `penyewa`
--
ALTER TABLE `penyewa`
  ADD PRIMARY KEY (`id_penyewa`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_transaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
