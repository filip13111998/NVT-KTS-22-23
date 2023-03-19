import { Component, ElementRef, ViewChild, AfterViewInit } from '@angular/core';

import * as d3 from 'd3';

// import 'd3';

// declare const d3: any;

@Component({
  selector: 'app-bar-chart',
  template: '<svg #chart width="500" height="300"></svg>'
})
export class BarChartComponent implements AfterViewInit{
  @ViewChild('chart') chartRef: ElementRef | undefined;

  ngAfterViewInit() {
    const data = [
      { year: 2010, value: 10 },
      { year: 2011, value: 20 },
      { year: 2012, value: 30 },
      { year: 2013, value: 40 },
      { year: 2014, value: 50 },
      { year: 2015, value: 60 },
    ];
    if (this.chartRef == undefined) {
      return;
    }
    const svg = d3.select(this.chartRef.nativeElement);
    const width = +svg.attr('width');
    const height = +svg.attr('height');
    const margin = { top: 20, right: 20, bottom: 30, left: 40 };
    const x = d3.scaleLinear().rangeRound([margin.left, width - margin.right]).domain(d3.extent(data, (d:any) => d.year) as [number, number]);
    const y = d3.scaleLinear().rangeRound([height - margin.bottom, margin.top]).domain([0, d3.max(data, (d:any) => d.value) as number]);
    const line = d3.line()
      .x((d:any) => x(d.year))
      .y((d:any) => y(d.value));
    svg.append('path')
      .datum(data)
      .attr('fill', 'none')
      .attr('stroke', 'steelblue')
      .attr('stroke-width', 2)
      .attr('d', line);
  }
}
