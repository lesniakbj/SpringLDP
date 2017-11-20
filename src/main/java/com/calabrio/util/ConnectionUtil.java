package com.calabrio.util;

/**
 * (c) Copyright 2017 Calabrio, Inc.
 * All Rights Reserved. www.calabrio.com LICENSED MATERIALS
 * Property of Calabrio, Inc., Minnesota, USA
 * <p>
 * No part of this publication may be reproduced, stored or transmitted,
 * in any form or by any means (electronic, mechanical, photocopying,
 * recording or otherwise) without prior written permission from Calabrio Software.
 * <p>
 * <p>
 * Created by Brendan.Lesniak on 11/20/2017.
 */
public class ConnectionUtil {
    public static String connectionString(String host, String port, String databaseName, String user, String pass) {
        return String.format("jdbc:sqlserver://%s:%s;database=%s;user=%s;password=%s;", host, port, databaseName, user, pass);
    }
}
