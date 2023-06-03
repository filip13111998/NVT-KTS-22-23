import { Component } from '@angular/core';
import * as d3 from 'd3';

@Component({
  selector: 'app-plot',
  templateUrl: './plot.component.html',
  styleUrls: ['./plot.component.css']
})
export class PlotComponent {
  ngOnInit(): void {
    this.createPlot();
  }

  createPlot(): void {
    // Assuming you have the list of objects stored in a variable called 'data'
    const data = [
      { date: new Date('2023-01-01'), kilometers: 100, price: 50, rideNumber: 1 },
      { date: new Date('2023-01-02'), kilometers: 150, price: 70, rideNumber: 2 },
      // ... more data objects
    ];

    const margin = { top: 20, right: 20, bottom: 30, left: 50 };
    const width = 600 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Create an SVG element within the container
    const svg = d3
      .select('#plotContainer')
      .append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
      .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // Define the scales for the x-axis and y-axis
    const xScale = d3
      .scaleTime()
      .domain(d3.extent(data, (d: { date: any; }) => d.date))
      .range([0, width]);

    const yScale = d3
      .scaleLinear()
      .domain([0, d3.max(data, (d: { kilometers: any; }) => d.kilometers)])
      .range([height, 0]);

    // Define the line functions for kilometers, price, and rideNumber
    const lineKilometers = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { kilometers: any; }) => yScale(d.kilometers));

    const linePrice = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { price: any; }) => yScale(d.price));

    const lineRideNumber = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { rideNumber: any; }) => yScale(d.rideNumber));

    // Add the line paths to the SVG
    svg
      .append('path')
      .datum(data)
      .attr('class', 'line')
      .attr('d', lineKilometers);

    svg
      .append('path')
      .datum(data)
      .attr('class', 'line')
      .attr('d', linePrice);

    svg
      .append('path')
      .datum(data)
      .attr('class', 'line')
      .attr('d', lineRideNumber);

    // Add data points to the SVG
    svg
      .selectAll('.data-point')
      .data(data)
      .enter()
      .append('circle')
      .attr('class', 'data-point')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.kilometers))
      .attr('r', 4)
      .style('fill', 'steelblue');

    svg
      .selectAll('.data-point-price')
      .data(data)
      .enter()
      .append('circle')
      .attr('class', 'data-point-price')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.price))
      .attr('r', 4)
      .style('fill', 'orange');

    svg
      .selectAll('.data-point-ride')
      .data(data)
      .enter()
      .append('circle')
      .attr('class', 'data-point-ride')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.rideNumber))
      .attr('r', 4)
      .style('fill', 'green');

    // Add x-axis to the SVG
    svg
      .append('g')
      .attr('class', 'x-axis')
      .attr('transform', 'translate(0,' + height + ')')
      .call(d3.axisBottom(xScale));

    // Add y-axis to the SVG
    svg
      .append('g')
      .attr('class', 'y-axis')
      .call(d3.axisLeft(yScale));

    // Add labels to the x-axis and y-axis
    svg
      .append('text')
      .attr('transform', 'translate(' + (width / 2) + ' ,' + (height + margin.top + 20) + ')')
      .style('text-anchor', 'middle')
      .text('Date');

    svg
      .append('text')
      .attr('transform', 'rotate(-90)')
      .attr('y', 0 - margin.left)
      .attr('x', 0 - (height / 2))
      .attr('dy', '1em')
      .style('text-anchor', 'middle')
      .text('Kilometers / Price / Ride Number');
  }

}
