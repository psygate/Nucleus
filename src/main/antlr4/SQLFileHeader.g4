/*
 *     Copyright (C) 2016 psygate (https://github.com/psygate)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 */

grammar SQLFileHeader;

@header {
package com.psygate.minecraft.spigot.sovereignty.nucleus.grammars;
}

//@lexer::header {com.psygate.minecraft.spigot.sovereignty.nucleus.grammars;}

WS  : (' '|'\r'|'\t'|'\u000C'|'\n')+ -> channel(HIDDEN)
    ;

SQL_COMMENT: '-'('-')+ -> channel(HIDDEN);

NUMBER: DIGITS | OCTAL_DIGITS | HEX_DIGITS;
fragment DIGITS: ('1'..'9' '0'..'9'*)|('0'+);
fragment OCTAL_DIGITS: '0' '0'..'7'+;
fragment HEX_DIGITS: '0x' ('0'..'9' | 'a'..'f' | 'A'..'F')+;
fragment COLON:      ':';
HEADER_MARKER:      'SCRIPT' WS* 'INFORMATION';
TYPE:               [A-z0-9_]+;
TYPE_MARKER:        ('Types'|'TYPES'|'types') COLON;
VERSION_MARKER:     ('Version'|'VERSION'|'version') COLON;
UPGRADE_MARKER:     ('Upgrades'|'UPGRADES'|'upgrades') COLON;


types:              TYPE_MARKER TYPE+;
versions:           VERSION_MARKER NUMBER;
upgrades:           UPGRADE_MARKER (NUMBER)+;
header:             HEADER_MARKER;
script:             header (types|versions|upgrades)+ header;