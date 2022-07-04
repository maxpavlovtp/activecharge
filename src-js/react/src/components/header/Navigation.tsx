import React from 'react';
import Links from './Links';
import styles from './Header.module.css';
import LinksTeblet from './LinksTablet';

export default function Navigation() {
  return (
    <div className={styles.navigation}>
      <LinksTeblet />
    </div>
  );
}
