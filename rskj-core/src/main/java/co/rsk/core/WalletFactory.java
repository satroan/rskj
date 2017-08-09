/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package co.rsk.core;

import org.ethereum.datasource.KeyValueDataSource;
import org.ethereum.datasource.LevelDbDataSource;

/**
 * Created by mario on 06/12/16.
 */
public class WalletFactory {
    private static final String oldWalletName = "wallet";
    private static final String walletName = "walletrlp";

    private WalletFactory() {

    }

    public static boolean existsPersistentWallet() {
        return existsPersistentWallet(walletName);
    }

    public static boolean existsPersistentWallet(String storeName) {
        LevelDbDataSource ds = new LevelDbDataSource(storeName);

        return ds.exists();
    }

    public static Wallet createPersistentWallet() {
        if (existsPersistentWallet(oldWalletName) && !existsPersistentWallet(walletName)) {
            Wallet wallet = createPersistentWallet(walletName);

            LevelDbDataSource ds = new LevelDbDataSource(oldWalletName);
            ds.init();

            return wallet;
        }

        return createPersistentWallet(walletName);
    }

    public static Wallet createPersistentWallet(String storeName) {
        Wallet wallet = new Wallet();
        KeyValueDataSource ds = new LevelDbDataSource(storeName);
        ds.init();
        wallet.setStore(ds);
        return wallet;
    }

    public static Wallet createWallet() {
        return new Wallet();
    }


}
