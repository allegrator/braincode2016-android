package com.zakaprov.braincode2016.inject;

import com.zakaprov.braincode2016.network.BraincodeNetworkClient;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class})
@Singleton
public interface NetworkComponent {

    BraincodeNetworkClient getBraincodeNetworkClient();
}
