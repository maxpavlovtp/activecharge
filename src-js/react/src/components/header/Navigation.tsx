import React from 'react';
import Links from './Links';
import styles from './Header.module.css';
import LinksTeblet from './LinksTablet';

export default function Navigation({stationNumbers}: {stationNumbers: any}) {
  return (
    <div className={styles.navigation}>
      <LinksTeblet stationNumbers={stationNumbers}/>
    </div>
  );
}
