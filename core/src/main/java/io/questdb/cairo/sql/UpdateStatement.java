/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2022 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.cairo.sql;

import io.questdb.std.Misc;
import io.questdb.std.ObjList;

import java.io.Closeable;

public class UpdateStatement implements Closeable {

    private final CharSequence updateTableName;
    private RecordCursorFactory rowIdFactory;
    private Function rowIdFilter;
    private final ObjList<Function> valuesFunctions;
    private final RecordMetadata valuesMetadata;
    private final int position;

    public UpdateStatement(
            CharSequence updateTableName,
            int position,
            RecordCursorFactory rowIdFactory,
            Function rowIdFilter,
            ObjList<Function> valuesFunctions,
            RecordMetadata valuesMetadata
    ) {
        this.updateTableName = updateTableName;
        this.position = position;
        this.rowIdFactory = rowIdFactory;
        this.rowIdFilter = rowIdFilter;
        this.valuesFunctions = valuesFunctions;
        this.valuesMetadata = valuesMetadata;
    }

    @Override
    public void close() {
        rowIdFactory = Misc.free(rowIdFactory);
        rowIdFilter = Misc.free(rowIdFilter);
        Misc.freeObjList(valuesFunctions);
        Misc.free(valuesMetadata);
    }

    public int getPosition() {
        return position;
    }

    public CharSequence getUpdateTableName() {
        return updateTableName;
    }

    public ObjList<Function> getValuesFunctions() {
        return valuesFunctions;
    }

    public RecordMetadata getValuesMetadata() {
        return valuesMetadata;
    }

    public RecordCursorFactory getRowIdFactory() {
        return rowIdFactory;
    }

    public Function getRowIdFilter() {
        return rowIdFilter;
    }
}
